package com.gn.web.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.sys.auth.bo.RouterVo;
import com.gn.web.sys.entity.SysMenu;
import com.gn.web.sys.model.TreeSelect;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单权限表分页数据
     *
     * @param page    分页参数
     * @param sysMenu 查询条件
     * @return IPage<SysMenu>
     */
    IPage<SysMenu> pageSysMenu(Page<SysMenu> page, SysMenu sysMenu);

    /**
     * 新增菜单权限表
     *
     * @param sysMenu 菜单权限表
     * @return boolean
     */
    boolean saveSysMenu(SysMenu sysMenu);

    /**
     * 删除菜单权限表
     *
     * @param menuId 主键
     * @return boolean
     */
    boolean removeSysMenu(String menuId);

    /**
     * 批量删除菜单权限表
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSysMenuByIds(List<String> ids);

    /**
     * 修改菜单权限表
     *
     * @param sysMenu 菜单权限表
     * @return boolean
     */
    boolean updateSysMenu(SysMenu sysMenu);

    List<SysMenu> getMenuByUserId(Long userId,SysMenu sysMenu);

    Set<String> getMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);


    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList(SysMenu sysMenu,Long userId);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);


    /**
     * 根据菜单编号获取详细信息
     * @param menuId
     * @return
     */
    SysMenu selectMenuByMenuId(Long menuId);


    /**
     * 校验菜单名称是否存在
     * @param sysMenu
     * @return
     */
    String checkMenuNameUnique(SysMenu sysMenu);

}
