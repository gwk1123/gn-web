package com.gn.web.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.sys.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 查询角色和菜单关联表分页数据
     *
     * @param page      分页参数
     * @param sysRoleMenu 查询条件
     * @return IPage<SysRoleMenu>
     */
     IPage<SysRoleMenu> pageSysRoleMenu(Page<SysRoleMenu> page,SysRoleMenu sysRoleMenu);

    /**
     * 新增角色和菜单关联表
     *
     * @param sysRoleMenu 角色和菜单关联表
     * @return boolean
     */
    boolean saveSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 删除角色和菜单关联表
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSysRoleMenu(String id);

    /**
     * 批量删除角色和菜单关联表
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSysRoleMenuByIds(List<String> ids);

    /**
     * 修改角色和菜单关联表
     *
     * @param sysRoleMenu 角色和菜单关联表
     * @return boolean
     */
    boolean updateSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * id查询数据
     *
     * @param id id
     * @return SysRoleMenu
     */
    SysRoleMenu getSysRoleMenuById(String id);
}
