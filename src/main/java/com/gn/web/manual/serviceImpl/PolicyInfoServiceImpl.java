package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.mapper.PolicyInfoMapper;
import com.gn.web.manual.service.PolicyInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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
        return this.save(policyInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyInfo(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyInfoByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePolicyInfo(PolicyInfo policyInfo){
        Assert.notNull(policyInfo, "全平台政策管理为空");
        return this.updateById(policyInfo);
    }

    @Override
    public PolicyInfo getPolicyInfoById(String id){
        return  this.getById(id);
    }
}
