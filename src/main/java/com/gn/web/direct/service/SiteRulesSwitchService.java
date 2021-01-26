package com.gn.web.direct.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.direct.entity.SiteRulesSwitch;

import java.util.List;

/**
 * <p>
 * 站点规则开关 服务类
 * </p>
 *
 * @author gwk
 * @since 2020-09-05
 */
public interface SiteRulesSwitchService extends IService<SiteRulesSwitch> {

    /**
     * 查询站点规则开关分页数据
     *
     * @param page      分页参数
     * @param siteRulesSwitch 查询条件
     * @return IPage<SiteRulesSwitch>
     */
     IPage<SiteRulesSwitch> pageSiteRulesSwitch(Page<SiteRulesSwitch> page,SiteRulesSwitch siteRulesSwitch);

    /**
     * 新增站点规则开关
     *
     * @param siteRulesSwitch 站点规则开关
     * @return boolean
     */
    boolean saveSiteRulesSwitch(SiteRulesSwitch siteRulesSwitch);

    /**
     * 删除站点规则开关
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSiteRulesSwitch(String id);

    /**
     * 批量删除站点规则开关
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSiteRulesSwitchByIds(List<String> ids);

    /**
     * 修改站点规则开关
     *
     * @param siteRulesSwitch 站点规则开关
     * @return boolean
     */
    boolean updateSiteRulesSwitch(SiteRulesSwitch siteRulesSwitch);

    /**
     * id查询数据
     *
     * @param id id
     * @return SiteRulesSwitch
     */
    SiteRulesSwitch getSiteRulesSwitchById(String id);
}
