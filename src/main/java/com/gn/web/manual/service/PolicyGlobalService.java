package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.PolicyGlobal;

import java.util.List;

/**
 * <p>
 * 全平台政策管理 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
public interface PolicyGlobalService extends IService<PolicyGlobal> {

    /**
     * 查询全平台政策管理分页数据
     *
     * @param page      分页参数
     * @param policyGlobal 查询条件
     * @return IPage<PolicyGlobal>
     */
     IPage<PolicyGlobal> pagePolicyGlobal(Page<PolicyGlobal> page, PolicyGlobal policyGlobal);

    /**
     * 新增全平台政策管理
     *
     * @param policyGlobal 全平台政策管理
     * @return boolean
     */
    boolean savePolicyGlobal(PolicyGlobal policyGlobal);

    /**
     * 删除全平台政策管理
     *
     * @param id 主键
     * @return boolean
     */
    boolean removePolicyGlobal(String id);

    /**
     * 批量删除全平台政策管理
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removePolicyGlobalByIds(List<String> ids);

    /**
     * 修改全平台政策管理
     *
     * @param policyGlobal 全平台政策管理
     * @return boolean
     */
    boolean updatePolicyGlobal(PolicyGlobal policyGlobal);

    /**
     * id查询数据
     *
     * @param id id
     * @return PolicyGlobal
     */
    PolicyGlobal getPolicyGlobalById(String id);
}
