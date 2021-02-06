package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.OtaSyncPolicy;

import java.util.List;

/**
 * <p>
 * OTA政策同步 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
public interface OtaSyncPolicyService extends IService<OtaSyncPolicy> {

    /**
     * 查询OTA政策同步分页数据
     *
     * @param page      分页参数
     * @param otaSyncPolicy 查询条件
     * @return IPage<OtaSyncPolicy>
     */
     IPage<OtaSyncPolicy> pageOtaSyncPolicy(Page<OtaSyncPolicy> page, OtaSyncPolicy otaSyncPolicy);

    /**
     * 新增OTA政策同步
     *
     * @param otaSyncPolicy OTA政策同步
     * @return boolean
     */
    boolean saveOtaSyncPolicy(OtaSyncPolicy otaSyncPolicy);

    /**
     * 删除OTA政策同步
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeOtaSyncPolicy(String id);

    /**
     * 批量删除OTA政策同步
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeOtaSyncPolicyByIds(List<String> ids);

    /**
     * 修改OTA政策同步
     *
     * @param otaSyncPolicy OTA政策同步
     * @return boolean
     */
    boolean updateOtaSyncPolicy(OtaSyncPolicy otaSyncPolicy);

    /**
     * id查询数据
     *
     * @param id id
     * @return OtaSyncPolicy
     */
    OtaSyncPolicy getOtaSyncPolicyById(String id);

    /**
     * 根据条件查询
     * @param otaSyncPolicy
     * @return
     */
    List<OtaSyncPolicy> selectOtaSyncPolicys(OtaSyncPolicy otaSyncPolicy);
}
