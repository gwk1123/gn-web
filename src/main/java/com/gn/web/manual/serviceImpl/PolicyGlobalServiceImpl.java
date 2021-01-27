package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.mapper.PolicyGlobalMapper;
import com.gn.web.manual.service.PolicyGlobalService;
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
public class PolicyGlobalServiceImpl extends ServiceImpl<PolicyGlobalMapper, PolicyGlobal> implements PolicyGlobalService {

    @Override
    public IPage<PolicyGlobal> pagePolicyGlobal(Page<PolicyGlobal> page, PolicyGlobal policyGlobal){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<PolicyGlobal> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePolicyGlobal(PolicyGlobal policyGlobal){
        Assert.notNull(policyGlobal, "全平台政策管理为空");
        return this.save(policyGlobal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyGlobal(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removePolicyGlobalByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePolicyGlobal(PolicyGlobal policyGlobal){
        Assert.notNull(policyGlobal, "全平台政策管理为空");
        return this.updateById(policyGlobal);
    }

    @Override
    public PolicyGlobal getPolicyGlobalById(String id){
        return  this.getById(id);
    }
}
