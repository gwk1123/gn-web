package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.manual.mapper.OtaRuleMapper;
import com.gn.web.manual.service.OtaRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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

    @Override
    public IPage<OtaRule> pageOtaRule(Page<OtaRule> page, OtaRule otaRule){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<OtaRule> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOtaRule(OtaRule otaRule){
        Assert.notNull(otaRule, "OTA航线规则为空");
        return this.save(otaRule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOtaRule(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOtaRuleByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOtaRule(OtaRule otaRule){
        Assert.notNull(otaRule, "OTA航线规则为空");
        return this.updateById(otaRule);
    }

    @Override
    public OtaRule getOtaRuleById(String id){
        return  this.getById(id);
    }
}
