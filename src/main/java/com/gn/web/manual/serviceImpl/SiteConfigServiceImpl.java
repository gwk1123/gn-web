package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.entity.SiteConfig;
import com.gn.web.manual.mapper.SiteConfigMapper;
import com.gn.web.manual.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 政策站点配置 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-01-26
 */
@Service
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfig> implements SiteConfigService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public IPage<SiteConfig> pageSiteConfig(Page<SiteConfig> page, SiteConfig siteConfig){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SiteConfig> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSiteConfig(SiteConfig siteConfig){
        Assert.notNull(siteConfig, "政策站点配置为空");
        boolean flay= this.save(siteConfig);
        saveOrUpdateCache( siteConfig);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSiteConfig(String id){
        Assert.hasText(id, "主键为空");
        boolean flay= this.removeById(id);
        SiteConfig siteConfig = this.getById(id);
        saveOrUpdateCache( siteConfig);
        return flay;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSiteConfigByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSiteConfig(SiteConfig siteConfig){
        Assert.notNull(siteConfig, "政策站点配置为空");
        boolean flay= this.updateById(siteConfig);
        saveOrUpdateCache( siteConfig);
        return flay;
    }

    @Override
    public SiteConfig getSiteConfigById(String id){
        return  this.getById(id);
    }

    public void saveOrUpdateCache(SiteConfig siteConfig){
        if(DirectConstants.NORMAL.equals(siteConfig.getStatus())){
            redisCache.addHashMap(DirectConstants.SITE_CONFIG,siteConfig.getOtaSiteCode(),siteConfig);
        }else {
            redisCache.removeHashKey(DirectConstants.SITE_CONFIG,siteConfig.getOtaSiteCode());
        }
    }

}
