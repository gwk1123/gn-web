package com.gn.web.manual.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.exception.CustomException;
import com.gn.web.manual.entity.OtaSyncPolicy;
import com.gn.web.manual.mapper.OtaSyncPolicyMapper;
import com.gn.web.manual.service.OtaSyncPolicyService;
import com.gn.web.source.entity.OtaRequest;
import com.gn.web.source.entity.SourceData;
import com.gn.web.source.service.SourceDataService;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


/**
 * <p>
 * OTA政策同步 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Service
public class OtaSyncPolicyServiceImpl extends ServiceImpl<OtaSyncPolicyMapper, OtaSyncPolicy> implements OtaSyncPolicyService {

    private Logger logger = LoggerFactory.getLogger(OtaSyncPolicyServiceImpl.class);
    @Override
    public IPage<OtaSyncPolicy> pageOtaSyncPolicy(Page<OtaSyncPolicy> page, OtaSyncPolicy otaSyncPolicy){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<OtaSyncPolicy> queryWrapper= buildQueryWrapper(otaSyncPolicy);
        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOtaSyncPolicy(OtaSyncPolicy otaSyncPolicy){
        Assert.notNull(otaSyncPolicy, "OTA政策同步为空");
        if(StringUtils.isEmpty(otaSyncPolicy.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        return this.save(otaSyncPolicy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOtaSyncPolicy(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOtaSyncPolicyByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOtaSyncPolicy(OtaSyncPolicy otaSyncPolicy){
        Assert.notNull(otaSyncPolicy, "OTA政策同步为空");
        if(StringUtils.isEmpty(otaSyncPolicy.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        return this.updateById(otaSyncPolicy);
    }

    @Override
    public OtaSyncPolicy getOtaSyncPolicyById(String id){
        return  this.getById(id);
    }

    public List<OtaSyncPolicy> selectOtaSyncPolicys(OtaSyncPolicy otaSyncPolicy){
        QueryWrapper<OtaSyncPolicy> queryWrapper= buildQueryWrapper(otaSyncPolicy);
        return this.list(queryWrapper);
    }

     public void updateOtaPolicy(List<String> uniqueKeys){

     }


    /**
     * 构建查询条件
     * @param otaSyncPolicy
     * @return
     */
    public QueryWrapper<OtaSyncPolicy> buildQueryWrapper(OtaSyncPolicy otaSyncPolicy) {
        if(StringUtils.isEmpty(otaSyncPolicy.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        QueryWrapper<OtaSyncPolicy> queryWrapper = new QueryWrapper<>();
        if (otaSyncPolicy != null) {
                queryWrapper.lambda()
                        .eq(OtaSyncPolicy::getOtaSiteCode, otaSyncPolicy.getOtaSiteCode())
                        .eq(!StringUtils.isEmpty(otaSyncPolicy.getTravelStartDate()), OtaSyncPolicy::getTravelStartDate, otaSyncPolicy.getTravelStartDate())
                        .eq(!StringUtils.isEmpty(otaSyncPolicy.getDepCity()), OtaSyncPolicy::getDepCity, otaSyncPolicy.getDepCity())
                        .eq(!StringUtils.isEmpty(otaSyncPolicy.getArrCity()), OtaSyncPolicy::getArrCity, otaSyncPolicy.getArrCity())
                        .eq(!StringUtils.isEmpty(otaSyncPolicy.getId()), OtaSyncPolicy::getId, otaSyncPolicy.getId())
                        .eq(!StringUtils.isEmpty(otaSyncPolicy.getAirline()), OtaSyncPolicy::getAirline, otaSyncPolicy.getAirline());
            }
        return queryWrapper;
    }


    @Autowired
    private SourceDataService sourceDataService;
    @Autowired
    @Qualifier(value = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static CloseableHttpClient httpClient;

    /**
     * 生成同步政策
     */
    @Async
    public void generateOtaSyncPolicy() {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(1000);
        cm.setDefaultMaxPerRoute(200);
        cm.setDefaultMaxPerRoute(500);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
        String url = "http://localhost:19095/manual_policy/search";

        List<SourceData> result = sourceDataService.list(new LambdaQueryWrapper<SourceData>().groupBy(SourceData::getDepCity, SourceData::getArrCity, SourceData::getTravelStartDate).select(SourceData::getDepCity, SourceData::getArrCity, SourceData::getTravelStartDate));
        if (!CollectionUtils.isEmpty(result)) {
            result.stream().forEach(e -> {
//
//                List<OtaRequest> otaRequestList =new ArrayList<>();
//                if(!CollectionUtils.isEmpty(otaRequestList) && otaRequestList.size() >=10){
//
//                }else if(!CollectionUtils.isEmpty(otaRequestList) && ){
//
//                }

                CompletableFuture.runAsync(() -> {
                    List<OtaRequest> otaRequestList = new ArrayList<>();
                    OtaRequest otaRequest = new OtaRequest();
                    otaRequest.setOtaSiteCode("CT001");
                    otaRequest.setFromAirport(e.getDepCity());
                    otaRequest.setFromDate(e.getTravelStartDate().format(fmt));
                    otaRequest.setToAirport(e.getArrCity());
                    otaRequest.setTripType("1");
                    otaRequestList.add(otaRequest);

                    HttpPost httpPost = new HttpPost(url);
                    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setConnectionRequestTimeout(200000).setSocketTimeout(200000).
                            setRedirectsEnabled(false).build();
                    httpPost.setConfig(requestConfig);
                    httpPost.addHeader("content-type", "application/json;charset=UTF-8");
                    httpPost.setEntity(new StringEntity(JSON.toJSONString(otaRequestList), Charset.forName("UTF-8")));
                    try {
                        httpClient.execute(httpPost);
                    } catch (IOException ioException) {
                        logger.error("发送同步政策请求失败,参数{},异常:{}", JSON.toJSONString(otaRequestList), ioException);
                    }


                }, threadPoolTaskExecutor);
            });
        }
    }


}
