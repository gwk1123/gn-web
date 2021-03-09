package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.exception.CustomException;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.redis.RedisCacheKeyUtils;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.entity.ResignConfig;
import com.gn.web.manual.mapper.ResignConfigMapper;
import com.gn.web.manual.service.ResignConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwk
 * @since 2020-11-17
 */
@Service
public class ResignConfigServiceImpl extends ServiceImpl<ResignConfigMapper, ResignConfig> implements ResignConfigService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public  IPage<ResignConfig> pageResignConfig(Page<ResignConfig> page, ResignConfig resignConfig){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<ResignConfig> queryWrapper = buildQueryWrapper(resignConfig);
        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveResignConfig(ResignConfig resignConfig){
        Assert.notNull(resignConfig, "为空");
        if(StringUtils.isEmpty(resignConfig.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        boolean flay= this.save(resignConfig);
        saveOrUpdateCache( resignConfig);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeResignConfig(String id){
        Assert.hasText(id, "主键为空");
        boolean flay= this.removeById(id);
        ResignConfig resignConfig = this.getById(id);
        saveOrUpdateCache( resignConfig);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeResignConfigByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        ids.stream().forEach(id ->{
            removeResignConfig( id);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateResignConfig(ResignConfig resignConfig){
        Assert.notNull(resignConfig, "全平台政策管理为空");
        if(StringUtils.isEmpty(resignConfig.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        boolean flay = this.updateById(resignConfig);
        saveOrUpdateCache( resignConfig);
        return flay;
    }

    @Override
    public ResignConfig getResignConfigById(String id){
        return  this.getById(id);
    }

    public QueryWrapper<ResignConfig> buildQueryWrapper(ResignConfig resignConfig){
        if(StringUtils.isEmpty(resignConfig)){
            throw new CustomException("获取站点code为空");
        }
        QueryWrapper<ResignConfig> queryWrapper =new QueryWrapper<>();
        if(resignConfig != null){
            queryWrapper.lambda().eq(ResignConfig::getOtaSiteCode,resignConfig.getOtaSiteCode())
                    .eq(!StringUtils.isEmpty(resignConfig.getAirline()),ResignConfig::getAirline,resignConfig.getAirline())
                    .eq(!StringUtils.isEmpty(resignConfig.getDepCity()),ResignConfig::getDepCity,resignConfig.getDepCity())
                    .eq(!StringUtils.isEmpty(resignConfig.getArrCity()),ResignConfig::getArrCity,resignConfig.getArrCity())
                    .eq(!StringUtils.isEmpty(resignConfig.getId()),ResignConfig::getId,resignConfig.getId());
        }
        return queryWrapper;
    }

//    public void saveOrUpdateCache(ResignConfig resignConfig) {
//        if (DirectConstants.NORMAL.equals(resignConfig.getStatus())) {
//            String setKey = RedisCacheKeyUtils.resignConfigSetKey(resignConfig);
//            redisCache.addSet(setKey, String.valueOf(resignConfig.getId()));
//            redisCache.addHashMap(DirectConstants.RESIGN_CONFIG, String.valueOf(resignConfig.getId()), resignConfig);
//        } else {
//            String setKey = RedisCacheKeyUtils.resignConfigSetKey(resignConfig);
//            redisCache.removeSetKey(setKey, String.valueOf(resignConfig.getId()));
//            redisCache.removeHashKey(DirectConstants.RESIGN_CONFIG, String.valueOf(resignConfig.getId()));
//        }
//    }

    public void saveOrUpdateCache(ResignConfig resignConfig){
        String devStr= StringUtils.isEmpty(resignConfig.getDepCity())?DirectConstants.AIRPORT_ALL:resignConfig.getDepCity();
        String arrString =  StringUtils.isEmpty(resignConfig.getArrCity())?DirectConstants.AIRPORT_ALL:resignConfig.getArrCity();
        resignConfig.setDepCity(devStr);
        resignConfig.setArrCity(arrString);
        if(DirectConstants.NORMAL.equals(resignConfig.getStatus())){
            redisCache.addHashMap(resignConfig.getOtaSiteCode()+"_"+resignConfig.getAirline(),String.valueOf(resignConfig.getId()),resignConfig);
        }else {
            redisCache.removeHashKey(resignConfig.getOtaSiteCode()+"_"+resignConfig.getAirline(),String.valueOf(resignConfig.getId()));
        }
    }

}
