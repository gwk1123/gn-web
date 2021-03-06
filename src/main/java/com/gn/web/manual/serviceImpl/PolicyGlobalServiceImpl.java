package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.exception.CustomException;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.redis.RedisCacheKeyUtils;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.mapper.PolicyGlobalMapper;
import com.gn.web.manual.service.PolicyGlobalService;
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
 * 全平台政策管理 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
@Service
public class PolicyGlobalServiceImpl extends ServiceImpl<PolicyGlobalMapper, PolicyGlobal> implements PolicyGlobalService {

    @Autowired
    private RedisCache redisCache;


    @Override
    public IPage<PolicyGlobal> pagePolicyGlobal(Page<PolicyGlobal> page, PolicyGlobal policyGlobal) {

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<PolicyGlobal> queryWrapper =  buildQueryWrapper(policyGlobal);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePolicyGlobal(PolicyGlobal policyGlobal) {
        Assert.notNull(policyGlobal, "全平台政策管理为空");
        if(StringUtils.isEmpty(policyGlobal.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        boolean flay= this.save(policyGlobal);
        saveOrUpdateCache( policyGlobal);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyGlobal(String id) {
        Assert.hasText(id, "主键为空");
        boolean flay= this.removeById(id);
        PolicyGlobal policyGlobal = this.getById(id);
        saveOrUpdateCache( policyGlobal);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyGlobalByIds(List<String> ids) {
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        ids.stream().forEach(id ->{
            removePolicyGlobal( id);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePolicyGlobal(PolicyGlobal policyGlobal) {
        Assert.notNull(policyGlobal, "全平台政策管理为空");
        if(StringUtils.isEmpty(policyGlobal.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        boolean flay = this.updateById(policyGlobal);
        saveOrUpdateCache( policyGlobal);
        return flay;
    }

    @Override
    public PolicyGlobal getPolicyGlobalById(String id) {
        return this.getById(id);
    }


    public QueryWrapper<PolicyGlobal> buildQueryWrapper(PolicyGlobal policyGlobal){
        if(StringUtils.isEmpty(policyGlobal.getOtaSiteCode())){
            throw new CustomException("获取站点code为空");
        }
        QueryWrapper<PolicyGlobal> queryWrapper =new QueryWrapper<>();
        if(policyGlobal != null){
            queryWrapper.lambda().eq(PolicyGlobal::getOtaSiteCode,policyGlobal.getOtaSiteCode())
                    .eq(!StringUtils.isEmpty(policyGlobal.getAirline()),PolicyGlobal::getAirline,policyGlobal.getAirline())
                    .eq(!StringUtils.isEmpty(policyGlobal.getDepAirport()),PolicyGlobal::getDepAirport,policyGlobal.getDepAirport())
                    .eq(!StringUtils.isEmpty(policyGlobal.getArrAirport()),PolicyGlobal::getArrAirport,policyGlobal.getArrAirport())
                    .eq(!StringUtils.isEmpty(policyGlobal.getId()),PolicyGlobal::getId,policyGlobal.getId());
        }
        return queryWrapper;
    }



    public void saveOrUpdateCache(PolicyGlobal policyGlobal) {
        String devStr= StringUtils.isEmpty(policyGlobal.getDepAirport())?DirectConstants.AIRPORT_ALL:policyGlobal.getDepAirport();
        String arrString =  StringUtils.isEmpty(policyGlobal.getArrAirport())?DirectConstants.AIRPORT_ALL:policyGlobal.getArrAirport();
        policyGlobal.setDepAirport(devStr);
        policyGlobal.setArrAirport(arrString);
        if(DirectConstants.NORMAL.equals(policyGlobal.getStatus())){
            String setKey = RedisCacheKeyUtils.policyGlobalSetKey(policyGlobal);
            redisCache.addSet(setKey, String.valueOf(policyGlobal.getId()));
            redisCache.addHashMap(DirectConstants.POLICY_GLOBAL, String.valueOf(policyGlobal.getId()), policyGlobal);
        }else{
            String setKey = RedisCacheKeyUtils.policyGlobalSetKey(policyGlobal);
            redisCache.removeSetKey(setKey, String.valueOf(policyGlobal.getId()));
            redisCache.removeHashKey(DirectConstants.POLICY_GLOBAL, String.valueOf(policyGlobal.getId()));
    }
}

}
