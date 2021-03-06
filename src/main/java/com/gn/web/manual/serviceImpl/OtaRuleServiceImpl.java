package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.exception.CustomException;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.entity.SiteConfig;
import com.gn.web.manual.mapper.OtaRuleMapper;
import com.gn.web.manual.service.OtaRuleService;
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
 * OTA航线规则 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
@Service
public class OtaRuleServiceImpl extends ServiceImpl<OtaRuleMapper, OtaRule> implements OtaRuleService {


    @Autowired
    private RedisCache redisCache;

    @Override
    public IPage<OtaRule> pageOtaRule(Page<OtaRule> page, OtaRule otaRule){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<OtaRule> queryWrapper = buildQueryWrapper( otaRule);
        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOtaRule(OtaRule otaRule){
        Assert.notNull(otaRule, "OTA航线规则为空");
        if(StringUtils.isEmpty(otaRule.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        if(StringUtils.isEmpty(otaRule.getRuleType())){
            throw new CustomException("获取规则类型为空");
        }
        boolean faly= this.save(otaRule);
        saveOrUpdateCache( otaRule);
        return faly;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOtaRule(String id){
        Assert.hasText(id, "主键为空");
        boolean faly= this.removeById(id);
        OtaRule otaRule =  this.getOtaRuleById(id);
        saveOrUpdateCache( otaRule);
        return faly;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOtaRuleByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        ids.stream().forEach(id ->{
            removeOtaRule( id);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOtaRule(OtaRule otaRule){
        Assert.notNull(otaRule, "OTA航线规则为空");
        if(StringUtils.isEmpty(otaRule.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        if(StringUtils.isEmpty(otaRule.getRuleType())){
            throw new CustomException("获取规则类型为空");
        }
        boolean faly = this.updateById(otaRule);
        saveOrUpdateCache( otaRule);
        return faly;
    }

    @Override
    public OtaRule getOtaRuleById(String id){
        return  this.getById(id);
    }

    public boolean changeStatus(OtaRule otaRule){
        OtaRule status =this.getById(otaRule.getId());
        status.setStatus(otaRule.getStatus());
        boolean flay= this.updateById(status);
        saveOrUpdateCache( status);
        return flay;
    }


    public QueryWrapper<OtaRule> buildQueryWrapper(OtaRule otaRule){
        if(StringUtils.isEmpty(otaRule.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        QueryWrapper<OtaRule> queryWrapper =new QueryWrapper<>();
        if(otaRule != null){
            queryWrapper.lambda().eq(OtaRule::getOtaSiteCode,otaRule.getOtaSiteCode())
                    .eq(!StringUtils.isEmpty(otaRule.getRuleType()),OtaRule::getRuleType,otaRule.getRuleType())
                    .eq(!StringUtils.isEmpty(otaRule.getId()),OtaRule::getId,otaRule.getId());
        }
        return queryWrapper;
    }

    public void saveOrUpdateCache(OtaRule otaRule){
        String devStr= StringUtils.isEmpty(otaRule.getDepAirport())?DirectConstants.AIRPORT_ALL:otaRule.getDepAirport();
        String arrString =  StringUtils.isEmpty(otaRule.getArrAirport())?DirectConstants.AIRPORT_ALL:otaRule.getArrAirport();
        otaRule.setDepAirport(devStr);
        otaRule.setArrAirport(arrString);
        if(DirectConstants.NORMAL.equals(otaRule.getStatus())){
            redisCache.addHashMap(otaRule.getOtaSiteCode()+"_"+otaRule.getRuleType(),String.valueOf(otaRule.getId()),otaRule);
        }else {
            redisCache.removeHashKey(otaRule.getOtaSiteCode()+"_"+otaRule.getRuleType(),String.valueOf(otaRule.getId()));
        }
    }

}
