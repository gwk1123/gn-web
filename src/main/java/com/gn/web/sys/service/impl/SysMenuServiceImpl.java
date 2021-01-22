package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.Constants;
import com.gn.web.common.constant.UserConstants;
import com.gn.web.sys.auth.bo.MetaVo;
import com.gn.web.sys.auth.bo.RouterVo;
import com.gn.web.sys.entity.SysMenu;
import com.gn.web.sys.entity.SysRoleMenu;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.entity.SysUserRole;
import com.gn.web.sys.mapper.SysMenuMapper;
import com.gn.web.sys.model.TreeSelect;
import com.gn.web.sys.service.SysMenuService;
import com.gn.web.sys.service.SysRoleMenuService;
import com.gn.web.sys.service.SysRoleService;
import com.gn.web.sys.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;

    public SysMenuServiceImpl(SysUserRoleService sysUserRoleService, SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    public  IPage<SysMenu> pageSysMenu(Page<SysMenu> page, SysMenu sysMenu){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSysMenu(SysMenu sysMenu){
        Assert.notNull(sysMenu, "菜单权限表为空");
        return this.save(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysMenu(String menuId){
        Assert.hasText(menuId, "主键为空");
        QueryWrapper<SysMenu> sysMenuQueryWrapper=new QueryWrapper<>();
        sysMenuQueryWrapper.lambda().eq(SysMenu::getMenuId,menuId);
        return this.removeById(sysMenuQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysMenuByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysMenu(SysMenu sysMenu){
        Assert.notNull(sysMenu, "菜单对象为空");
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysMenu::getMenuId,sysMenu.getMenuId());
        return this.update(sysMenu,queryWrapper);
    }


    public SysMenu selectMenuByMenuId(Long menuId){
        QueryWrapper<SysMenu> sysMenuQueryWrapper=new QueryWrapper<>();
        sysMenuQueryWrapper.lambda().eq(SysMenu::getMenuId,menuId);
        return this.getOne(sysMenuQueryWrapper);
    }


    @Override
    public List<SysMenu> getMenuByUserId(Long userId,SysMenu sysMenu){
        List<SysRoleMenu> roleMenus = this.getRoleMenuByUserId(userId);
        if(CollectionUtils.isEmpty(roleMenus)){
            return null;
        }
        List<Long> menuIds = roleMenus.stream().filter(Objects::nonNull).map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        QueryWrapper<SysMenu> menuQueryWrapper=new QueryWrapper<>();
        this.buildQueryWrapper( sysMenu, menuQueryWrapper);
        menuQueryWrapper.lambda().in(SysMenu::getMenuId,menuIds).orderByDesc(SysMenu::getParentId);
        List<SysMenu> menus= this.list(menuQueryWrapper);
        return menus;
    }

    @Override
    public Set<String> getMenuPermsByUserId(Long userId){
        List<SysMenu> menus= getMenuByUserId( userId,null);
        if(CollectionUtils.isEmpty(menus)){
            return null;
        }
        return menus.stream().filter(Objects::nonNull).map(SysMenu::getPerms).collect(Collectors.toSet());
    }


    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId)
    {
        List<SysMenu> menus = null;

        if (userId != null && 1L == userId)
        {

            QueryWrapper<SysMenu> menuQueryWrapper=new QueryWrapper<>();
            menuQueryWrapper.lambda().eq(SysMenu::getStatus,"0")
                    .in(SysMenu::getMenuType, Arrays.asList(Constants.MENUTYPE.split(",")));
            menus = this.list(menuQueryWrapper);
            if(CollectionUtils.isEmpty(menus)){
                return menus;
            }
            menus.stream().filter(Objects::nonNull).map(m ->{
                if(StringUtils.isEmpty(m.getPerms())){
                   m.setPerms("");
                }
                return m;
            }).collect(Collectors.toList());
        }
        else
        {
            List<SysRoleMenu> roleMenus = this.getRoleMenuByUserId(userId);
            if(CollectionUtils.isEmpty(roleMenus)){
                return null;
            }
            List<Long> menuIds = roleMenus.stream().filter(Objects::nonNull).map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            QueryWrapper<SysMenu> menuQueryWrapper=new QueryWrapper<>();
            menuQueryWrapper.lambda().eq(SysMenu::getStatus,"0")
                .in(SysMenu::getMenuType, Arrays.asList(Constants.MENUTYPE.split(",")))
                    .in(SysMenu::getMenuId,menuIds).orderByDesc(SysMenu::getParentId);
            menus= this.list(menuQueryWrapper);
        }
        return getChildPerms(menus, 0);
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMeunFrame(menu))
            {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMeunFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu)
    {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame()))
        {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMeunFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }


    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu))
        {
            component = menu.getComponent();
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMeunFrame(SysMenu menu)
    {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }


    /**
     * 跟据用户名获取角色菜单关联表
     * @param userId
     * @return
     */
    public List<SysRoleMenu> getRoleMenuByUserId(Long userId) {
        QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.lambda().eq(SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = sysUserRoleService.list(userRoleQueryWrapper);
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }
        List<Long> roleIds = userRoles.stream().filter(Objects::nonNull).map(SysUserRole::getRoleId).collect(Collectors.toList());
        QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.lambda().in(SysRoleMenu::getRoleId, roleIds);
        return sysRoleMenuService.list(roleMenuQueryWrapper);
    }


    /**
     * 参数的查询条件
     * @param sysMenu
     * @param sysMenuQueryWrapper
     * @return
     */
    private void buildQueryWrapper(SysMenu sysMenu,QueryWrapper<SysMenu> sysMenuQueryWrapper) {
        if (sysMenu != null) {
            sysMenuQueryWrapper.lambda()
                    .like(StringUtils.isNotBlank(sysMenu.getMenuName()), SysMenu::getMenuName, sysMenu.getMenuName())
                    .eq(StringUtils.isNotBlank(sysMenu.getMenuType()), SysMenu::getMenuType, sysMenu.getMenuType())
                    .eq(StringUtils.isNotBlank(sysMenu.getVisible()),SysMenu::getVisible,sysMenu.getVisible())
                    .eq(StringUtils.isNotBlank(sysMenu.getStatus()), SysMenu::getStatus, sysMenu.getStatus())
                    .orderByDesc(SysMenu::getParentId);
        }
    }



    /**
     * 根据用户查询系统菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu sysMenu, Long userId) {
        List<SysMenu> menuList = null;
        QueryWrapper<SysMenu> queryWrapper =new QueryWrapper<>();
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            this.buildQueryWrapper( sysMenu,queryWrapper);
            menuList = this.list(queryWrapper);
        } else {
            List<Long> roleIds = sysRoleService.selectRoleListByUserId(userId);
            if (CollectionUtils.isEmpty(roleIds)) {
                return null;
            }
            QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper = new QueryWrapper<>();
            sysRoleMenuQueryWrapper.lambda().in(SysRoleMenu::getRoleId, roleIds);
            List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(sysRoleMenuQueryWrapper);
            if (CollectionUtils.isEmpty(sysRoleMenus)) {
                return null;
            }
            List<Long> sysRoleMenuIds = sysRoleMenus.stream().filter(Objects::nonNull).map(SysRoleMenu::getMenuId).collect(Collectors.toList());
            this.buildQueryWrapper( sysMenu,queryWrapper);
            queryWrapper.lambda().in(SysMenu::getMenuId, sysRoleMenuIds);
            menuList = this.list(queryWrapper);

        }
        return menuList;
    }



    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId)
    {
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper=new QueryWrapper<>();
        sysRoleMenuQueryWrapper.lambda().eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenus= sysRoleMenuService.list(sysRoleMenuQueryWrapper);
        if(CollectionUtils.isEmpty(sysRoleMenus)){
            return null;
        }
        List<Long> menuIds = sysRoleMenus.stream().filter(Objects::nonNull).map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        QueryWrapper<SysMenu> sysMenuQueryWrapper=new QueryWrapper<>();
        sysMenuQueryWrapper.lambda().in(SysMenu::getMenuId,menuIds);
        List<SysMenu> sysMenus = this.list(sysMenuQueryWrapper);
        if(CollectionUtils.isEmpty(sysMenus)){
            return null;
        }
        List<Long> parentIds = sysMenus.stream().filter(Objects::nonNull).map(SysMenu::getParentId).collect(Collectors.toList());

        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().in(SysMenu::getMenuId,menuIds).notIn(SysMenu::getMenuId,parentIds);
        List<SysMenu> sysMenuList = this.list(queryWrapper);
        if(CollectionUtils.isEmpty(sysMenuList)){
            return null;
        }
        return sysMenuList.stream().filter(Objects::nonNull).map(SysMenu::getMenuId).collect(Collectors.toList());
    }


    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus)
    {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0)
            {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }


    /**
     * 校验菜单名称是否唯一
     *
     * @param sysMenu 菜单信息
     * @return 结果
     */
    public String checkMenuNameUnique(SysMenu sysMenu){
        Long menuId = sysMenu.getMenuId()==null ? -1L : sysMenu.getMenuId();
        QueryWrapper<SysMenu> sysMenuQueryWrapper=new QueryWrapper<>();
        sysMenuQueryWrapper.lambda().eq(SysMenu::getMenuName,sysMenu.getMenuName())
                .eq(SysMenu::getParentId,sysMenu.getParentId());
        SysMenu info = this.getOne(sysMenuQueryWrapper);
        if (info != null && info.getMenuId().longValue() != menuId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

}
