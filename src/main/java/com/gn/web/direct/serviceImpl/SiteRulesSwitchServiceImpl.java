package com.gn.web.direct.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.direct.entity.SiteRulesSwitch;
import com.gn.web.direct.mapper.SiteRulesSwitchMapper;
import com.gn.web.direct.service.SiteRulesSwitchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 站点规则开关 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2020-09-05
 */
@Service
public class SiteRulesSwitchServiceImpl extends ServiceImpl<SiteRulesSwitchMapper, SiteRulesSwitch> implements SiteRulesSwitchService {

//    @Autowired
//    private SiteRulesSwitchRepositoryImpl siteRulesSwitchRepository;

    @Override
    public  IPage<SiteRulesSwitch> pageSiteRulesSwitch(Page<SiteRulesSwitch> page, SiteRulesSwitch siteRulesSwitch){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SiteRulesSwitch> queryWrapper = this.buildQueryWrapper(siteRulesSwitch);
        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSiteRulesSwitch(SiteRulesSwitch siteRulesSwitch){
        Assert.notNull(siteRulesSwitch, "站点规则开关为空");
        this.save(siteRulesSwitch);
//        if(DirectConstants.NORMAL.equals(siteRulesSwitch.getStatus())){
//            siteRulesSwitchRepository.saveOrUpdateCache(siteRulesSwitch);
//        }else {
//            siteRulesSwitchRepository.delete(siteRulesSwitch);
//        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSiteRulesSwitch(String id){
        Assert.hasText(id, "主键为空");
        SiteRulesSwitch siteRulesSwitch=this.getById(id);
        siteRulesSwitch.setStatus(DirectConstants.DELETE);
        this.updateById(siteRulesSwitch);
//        siteRulesSwitchRepository.delete(siteRulesSwitch);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSiteRulesSwitchByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        ids.stream().forEach(e ->{
            removeSiteRulesSwitch(e);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSiteRulesSwitch(SiteRulesSwitch siteRulesSwitch){
        Assert.notNull(siteRulesSwitch, "站点规则开关为空");
        this.updateById(siteRulesSwitch);
//        if(DirectConstants.NORMAL.equals(siteRulesSwitch.getStatus())){
//            siteRulesSwitchRepository.saveOrUpdateCache(siteRulesSwitch);
//        }else {
//            siteRulesSwitchRepository.delete(siteRulesSwitch);
//        }
        return true;
    }

    @Override
    public SiteRulesSwitch getSiteRulesSwitchById(String id){
        return  this.getById(id);
    }


    public QueryWrapper<SiteRulesSwitch> buildQueryWrapper(SiteRulesSwitch siteRulesSwitch){
        QueryWrapper<SiteRulesSwitch> gdsQueryWrapper=new QueryWrapper<>();
        if(siteRulesSwitch != null){
            gdsQueryWrapper.lambda().like(StringUtils.isNotBlank(siteRulesSwitch.getGroupKey()),SiteRulesSwitch::getGroupKey,siteRulesSwitch.getGroupKey())
                    .like(StringUtils.isNotBlank(siteRulesSwitch.getParameterKey()),SiteRulesSwitch::getParameterKey,siteRulesSwitch.getParameterKey());
        }
        if(siteRulesSwitch != null && StringUtils.isNotBlank(siteRulesSwitch.getStatus())){
            gdsQueryWrapper.lambda().eq(StringUtils.isNotBlank(siteRulesSwitch.getStatus()),SiteRulesSwitch::getStatus,siteRulesSwitch.getStatus());
        }else {
            gdsQueryWrapper.and(wrapper -> wrapper.lambda().eq(SiteRulesSwitch::getStatus, DirectConstants.NORMAL).or().eq(SiteRulesSwitch::getStatus,DirectConstants.FAILURE));
        }
        gdsQueryWrapper.lambda().orderByAsc(SiteRulesSwitch::getCreateTime);
        return gdsQueryWrapper;
    }
}
