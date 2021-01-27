package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.PolicyInfo;

import java.util.List;

/**
 * <p>
 * 全平台政策管理 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
public interface PolicyInfoService extends IService<PolicyInfo> {

    /**
     * 查询全平台政策管理分页数据
     *
     * @param page      分页参数
     * @param policyInfo 查询条件
     * @return IPage<PolicyInfo>
     */
     IPage<PolicyInfo> pagePolicyInfo(Page<PolicyInfo> page, PolicyInfo policyInfo);

    /**
     * 新增全平台政策管理
     *
     * @param policyInfo 全平台政策管理
     * @return boolean
     */
    boolean savePolicyInfo(PolicyInfo policyInfo);

    /**
     * 删除全平台政策管理
     *
     * @param id 主键
     * @return boolean
     */
    boolean removePolicyInfo(String id);

    /**
     * 批量删除全平台政策管理
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removePolicyInfoByIds(List<String> ids);

    /**
     * 修改全平台政策管理
     *
     * @param policyInfo 全平台政策管理
     * @return boolean
     */
    boolean updatePolicyInfo(PolicyInfo policyInfo);

    /**
     * id查询数据
     *
     * @param id id
     * @return PolicyInfo
     */
    PolicyInfo getPolicyInfoById(String id);
}
