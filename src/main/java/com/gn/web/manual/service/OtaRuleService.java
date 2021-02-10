package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.OtaRule;

import java.util.List;

/**
 * <p>
 * OTA航线规则 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
public interface OtaRuleService extends IService<OtaRule> {

    /**
     * 查询OTA航线规则分页数据
     *
     * @param page      分页参数
     * @param otaRule 查询条件
     * @return IPage<OtaRule>
     */
     IPage<OtaRule> pageOtaRule(Page<OtaRule> page, OtaRule otaRule);

    /**
     * 新增OTA航线规则
     *
     * @param otaRule OTA航线规则
     * @return boolean
     */
    boolean saveOtaRule(OtaRule otaRule);

    /**
     * 删除OTA航线规则
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeOtaRule(String id);

    /**
     * 批量删除OTA航线规则
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeOtaRuleByIds(List<String> ids);

    /**
     * 修改OTA航线规则
     *
     * @param otaRule OTA航线规则
     * @return boolean
     */
    boolean updateOtaRule(OtaRule otaRule);

    /**
     * id查询数据
     *
     * @param id id
     * @return OtaRule
     */
    OtaRule getOtaRuleById(String id);

    boolean changeStatus(OtaRule otaRule);
}
