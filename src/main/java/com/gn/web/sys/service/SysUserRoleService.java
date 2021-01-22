package com.gn.web.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.entity.SysUserRole;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 查询用户和角色关联表分页数据
     *
     * @param page      分页参数
     * @param sysUserRole 查询条件
     * @return IPage<SysUserRole>
     */
     IPage<SysUserRole> pageSysUserRole(Page<SysUserRole> page,SysUserRole sysUserRole);

    /**
     * 新增用户和角色关联表
     *
     * @param sysUserRole 用户和角色关联表
     * @return boolean
     */
    boolean saveSysUserRole(SysUserRole sysUserRole);

    /**
     * 删除用户和角色关联表
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSysUserRole(String id);


    /**
     * id查询数据
     *
     * @param id id
     * @return SysUserRole
     */
    SysUserRole getSysUserRoleById(String id);

    /**
     * 保存用户和角色关系
     * @param sysUser
     */
    void saveUserRole(SysUser sysUser);

    /**
     * 删除用户和角色关系
     * @param userId
     */
    void deleteUserRoleByUserId(Long userId);
}
