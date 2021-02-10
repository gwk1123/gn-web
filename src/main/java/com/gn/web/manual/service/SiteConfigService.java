package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.SiteConfig;

import java.util.List;

/**
 * <p>
 * 政策站点配置 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-26
 */
public interface SiteConfigService extends IService<SiteConfig> {

    /**
     * 查询政策站点配置分页数据
     *
     * @param page      分页参数
     * @param siteConfig 查询条件
     * @return IPage<SiteConfig>
     */
     IPage<SiteConfig> pageSiteConfig(Page<SiteConfig> page, SiteConfig siteConfig);

    /**
     * 新增政策站点配置
     *
     * @param siteConfig 政策站点配置
     * @return boolean
     */
    boolean saveSiteConfig(SiteConfig siteConfig);

    /**
     * 删除政策站点配置
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSiteConfig(String id);

    /**
     * 批量删除政策站点配置
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSiteConfigByIds(List<String> ids);

    /**
     * 修改政策站点配置
     *
     * @param siteConfig 政策站点配置
     * @return boolean
     */
    boolean updateSiteConfig(SiteConfig siteConfig);

    /**
     * id查询数据
     *
     * @param id id
     * @return SiteConfig
     */
    SiteConfig getSiteConfigById(String id);

    boolean changeStatus(SiteConfig siteConfig);
}
