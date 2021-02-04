package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.manual.entity.OtaSyncPolicy;
import com.gn.web.manual.mapper.OtaSyncPolicyMapper;
import com.gn.web.manual.service.OtaSyncPolicyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


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

    @Override
    public IPage<OtaSyncPolicy> pageOtaSyncPolicy(Page<OtaSyncPolicy> page, OtaSyncPolicy otaSyncPolicy){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<OtaSyncPolicy> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOtaSyncPolicy(OtaSyncPolicy otaSyncPolicy){
        Assert.notNull(otaSyncPolicy, "OTA政策同步为空");
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
        return this.updateById(otaSyncPolicy);
    }

    @Override
    public OtaSyncPolicy getOtaSyncPolicyById(String id){
        return  this.getById(id);
    }
}
