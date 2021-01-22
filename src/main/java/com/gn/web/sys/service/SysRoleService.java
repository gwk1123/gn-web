package com.gn.web.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.sys.entity.SysRole;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询角色信息表分页数据
     *
     * @param page      分页参数
     * @param sysRole 查询条件
     * @return IPage<SysRole>
     */
     IPage<SysRole> pageSysRole(Page<SysRole> page,SysRole sysRole);

    /**
     * 新增角色信息表
     *
     * @param sysRole 角色信息表
     * @return boolean
     */
    int saveSysRole(SysRole sysRole);

    /**
     * 删除角色信息表
     *
     * @param roleId 主键
     * @return boolean
     */
    boolean removeSysRole(String roleId);


    /**
     * 修改角色信息表
     *
     * @param sysRole 角色信息表
     * @return boolean
     */
    int updateSysRole(SysRole sysRole);

    /**
     * id查询数据
     *
     * @param id id
     * @return SysRole
     */
    SysRole getSysRoleByRoleId(String id);

    /**
     * 跟据用户名称获取角色集合
     * @param userId
     * @return
     */
    Set<String> getRoleByUserId(Long userId);

    /**
     * 获取所有角色
     * @return
     */
    List<SysRole> selectRoleAll();

    /**
     * 跟据用户ID获取角色集合ID
     * @param userId
     * @return
     */
    List<Long> selectRoleListByUserId(Long userId);


    /**
     * 校验角色名和权限字符
     * @param role
     * @return
     */
    String checkRoleNameOrRoleKeyUnique(SysRole role,String type);

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    void checkRoleAllowed(SysRole role);

}
