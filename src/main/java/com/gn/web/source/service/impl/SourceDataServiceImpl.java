package com.gn.web.source.service.impl;

import cn.hutool.core.date.SystemClock;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.utils.GzipUtil;
import com.gn.web.source.entity.OtaRequest;
import com.gn.web.source.entity.SourceData;
import com.gn.web.source.entity.SourceDataSegment;
import com.gn.web.source.mapper.SourceDataMapper;
import com.gn.web.source.service.SourceDataSegmentService;
import com.gn.web.source.service.SourceDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;


/**
 * <p>
 * 政策数据源 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-02-05
 */
@Service
public class SourceDataServiceImpl extends ServiceImpl<SourceDataMapper, SourceData> implements SourceDataService {


    @Autowired
    private SourceDataSegmentService sourceDataSegmentService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier(value = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private Logger logger =LoggerFactory.getLogger(SourceDataServiceImpl.class);

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");


    @Override
    public  IPage<SourceData> pageSourceData(Page<SourceData> page, SourceData sourceData){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SourceData> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSourceData(SourceData sourceData){
        Assert.notNull(sourceData, "政策数据源为空");
        return this.save(sourceData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourceData(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourceDataByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSourceData(SourceData sourceData){
        Assert.notNull(sourceData, "政策数据源为空");
        return this.updateById(sourceData);
    }

    @Override
    public SourceData getSourceDataById(String id){
        return  this.getById(id);
    }

    /**
     * 将数据库的初始化到缓存中
     * */
    @Async
    public void sourceDataCache(){
        List<SourceData> result=this.list(new LambdaQueryWrapper<SourceData>().groupBy(SourceData::getDepCity,SourceData::getArrCity,SourceData::getTravelStartDate).select(SourceData::getDepCity,SourceData::getArrCity,SourceData::getTravelStartDate));
        CountDownLatch countDownLatch = new CountDownLatch(result.size());
        if(!CollectionUtils.isEmpty(result)){
            result.stream().forEach(e ->{
                CompletableFuture.runAsync(()->{
                    String uid = UUID.randomUUID().toString().replaceAll("-","");
                    Long t1 = SystemClock.now();
                    QueryWrapper<SourceData>queryWrapper=new QueryWrapper<>();
                    queryWrapper.lambda().eq(SourceData::getTravelStartDate,e.getTravelStartDate())
                            .eq(SourceData::getDepCity,e.getDepCity())
                            .eq(SourceData::getArrCity,e.getArrCity());
                   List<SourceData>sourceDataList= this.list(queryWrapper);
                    Long c1 = SystemClock.now();
                    logger.info("uuid:{},c1耗时:{}",uid,c1-t1);

                    List<Long> ids = sourceDataList.stream().map(m -> m.getId()).collect(Collectors.toList());
                    QueryWrapper<SourceDataSegment> queryWrapperSegment = new QueryWrapper<>();
                    queryWrapperSegment.lambda().in(SourceDataSegment::getSourceDataId, ids);
                    Long e1 = SystemClock.now();
                    List<SourceDataSegment> sourceDataSegments = sourceDataSegmentService.list(queryWrapperSegment);
                    Long e2 = SystemClock.now();
                    logger.info("uuid:{},e1耗时:{}", uid, e2 - e1);

                    Map<Long, List<SourceDataSegment>> sourceDataSegmentMap = sourceDataSegments.stream().collect(Collectors.groupingBy(g -> g.getSourceDataId()));
                    sourceDataList.parallelStream().forEach(s ->{
                        s.setSourceDataSegments(sourceDataSegmentMap.get(s.getId()));
                    });

                    Long c2 = SystemClock.now();
                    logger.info("uuid:{},c2耗时:{}",uid,c2-c1);
                    Map<String,List<SourceData>> sourceDataMap = sourceDataList.stream().collect(Collectors.groupingBy(
                            g ->g.getSourceType()
                    ));
                    OtaRequest otaRequest =new OtaRequest();
                    otaRequest.setFromAirport(e.getDepCity());
                    otaRequest.setToAirport(e.getArrCity());
                    otaRequest.setFromDate(e.getTravelStartDate().format(fmt));
                    String key =getRedisKey( otaRequest);

                    Map<String,byte[]> cacheMap = new ConcurrentHashMap<>();
                    sourceDataMap.entrySet().stream().forEach( s ->{
                        String setKey = s.getKey();
                        String str = JSON.toJSONString(s.getValue());
                        byte[] res=GzipUtil.compressStream(str).toByteArray();
                        cacheMap.put(setKey,res);
                    });
                    redisCache.addHashMapAll(key,cacheMap);
                    Long t2 = SystemClock.now();
                    logger.info("uuid:{},总耗时:{}",uid,t2-t1);
                    countDownLatch.countDown();
                    logger.info("uuid{},还剩余处理的数据{}:",uid,countDownLatch.getCount());
                },threadPoolTaskExecutor);
            });
        }
    }


    public String getRedisKey(OtaRequest otaRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(otaRequest.getFromAirport());
        stringBuffer.append(otaRequest.getToAirport());
        stringBuffer.append(otaRequest.getFromDate());
        if ("2".equals(otaRequest.getTripType())) {
            stringBuffer.append(otaRequest.getRetDate());
        }
        return stringBuffer.toString();
    }

}
