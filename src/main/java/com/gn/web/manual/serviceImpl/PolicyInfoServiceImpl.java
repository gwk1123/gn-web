package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.common.redis.RedisCacheKeyUtils;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.mapper.PolicyInfoMapper;
import com.gn.web.manual.service.PolicyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.ws.Action;
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
public class PolicyInfoServiceImpl extends ServiceImpl<PolicyInfoMapper, PolicyInfo> implements PolicyInfoService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public IPage<PolicyInfo> pagePolicyInfo(Page<PolicyInfo> page, PolicyInfo policyInfo){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<PolicyInfo> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePolicyInfo(PolicyInfo policyInfo){
        Assert.notNull(policyInfo, "全平台政策管理为空");
        boolean flay= this.save(policyInfo);
        saveOrUpdateCache( policyInfo);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyInfo(String id){
        Assert.hasText(id, "主键为空");
        boolean flay =this.removeById(id);
        PolicyInfo policyInfo = this.getById(id);
        saveOrUpdateCache( policyInfo);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyInfoByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        ids.stream().forEach(id ->{
            removePolicyInfo( id);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePolicyInfo(PolicyInfo policyInfo){
        Assert.notNull(policyInfo, "全平台政策管理为空");
        boolean flay= this.updateById(policyInfo);
        saveOrUpdateCache( policyInfo);
        return flay;
    }

    @Override
    public PolicyInfo getPolicyInfoById(String id){
        return  this.getById(id);
    }

    public void saveOrUpdateCache(PolicyInfo policyInfo) {
        String devStr= StringUtils.isEmpty(policyInfo.getDepAirport())?DirectConstants.AIRPORT_ALL:policyInfo.getDepAirport();
        String arrString =  StringUtils.isEmpty(policyInfo.getArrAirport())?DirectConstants.AIRPORT_ALL:policyInfo.getArrAirport();
        policyInfo.setDepAirport(devStr);
        policyInfo.setArrAirport(arrString);
        if (DirectConstants.NORMAL.equals(policyInfo.getStatus())) {
            String setKey = RedisCacheKeyUtils.policyInfoSetKey(policyInfo);
            redisCache.addSet(setKey, String.valueOf(policyInfo.getId()));
            redisCache.addHashMap(DirectConstants.POLICY_GLOBAL, String.valueOf(policyInfo.getId()), policyInfo);
        } else {
            String setKey = RedisCacheKeyUtils.policyInfoSetKey(policyInfo);
            redisCache.removeSetKey(setKey, String.valueOf(policyInfo.getId()));
            redisCache.removeHashKey(DirectConstants.POLICY_GLOBAL, String.valueOf(policyInfo.getId()));
        }
    }

}
