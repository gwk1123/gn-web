package com.gn.web.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.constant.Constants;
import com.gn.web.common.constant.UserConstants;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.common.utils.ServletUtils;
import com.gn.web.common.utils.StringUtils;
import com.gn.web.sys.entity.SysMenu;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.service.SysMenuService;
import com.gn.web.sys.service.impl.SysLoginServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Api(tags = {"菜单权限表"})
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final SysLoginServiceImpl sysLoginServiceImpl;

    public SysMenuController(SysMenuService ysMenuService, SysLoginServiceImpl sysLoginServiceImpl) {
        this.sysMenuService = ysMenuService;
        this.sysLoginServiceImpl = sysLoginServiceImpl;
    }

    @ApiOperation(value = "删除菜单权限表")
    @DeleteMapping("/{menuId}")
    public boolean removeSysMenu(@PathVariable("menuId") String menuId) {
        return sysMenuService.removeSysMenu(menuId);
    }


    @ApiOperation(value = "查询菜单权限表分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数"),
            @ApiImplicitParam(name = "sysMenu", value = "查询条件")
    })
    @GetMapping("/sysMenu/page")
    public IPage<SysMenu> pageSysMenu(Page<SysMenu> page, SysMenu sysMenu) {
        return sysMenuService.pageSysMenu(page, sysMenu);
    }


    /**
     * 获取菜单列表
     */
    @ApiOperation(value = "获取菜单列表")
    @GetMapping("/list")
    public CommonResult list(SysMenu menu) {
        SysUser sysUser = SecurityUtils.getLoginUser();
        Long userId = sysUser.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, userId);
        return CommonResult.success(menus);
    }


    /**
     * 根据菜单编号获取详细信息
     */
    @ApiOperation(value = "根据菜单编号获取详细信息")
    @GetMapping(value = "/{menuId}")
    public CommonResult getMenuInfo(@PathVariable Long menuId) {
        return CommonResult.success(sysMenuService.selectMenuByMenuId(menuId));
    }


    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @ApiOperation(value = "新增菜单")
    @PostMapping
    public CommonResult add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(sysMenuService.checkMenuNameUnique(menu))) {
            return CommonResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return CommonResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return CommonResult.success(sysMenuService.saveSysMenu(menu));
    }


    /**
     * 修改菜单
     */
    @ApiOperation(value = "修改菜单")
    @PutMapping
    public CommonResult updateSysMenu(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(sysMenuService.checkMenuNameUnique(menu))) {
            return CommonResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
            return CommonResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return CommonResult.error("新增菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return CommonResult.success(sysMenuService.updateSysMenu(menu));
    }


    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public CommonResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        // 用户信息
        SysUser user = sysLoginServiceImpl.getLoginUser(ServletUtils.getRequest());
        List<SysMenu> menus = sysMenuService.selectMenuList(new SysMenu(), user.getUserId());
        CommonResult commonResult = CommonResult.success();
        commonResult.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        commonResult.put("menus", sysMenuService.buildMenuTreeSelect(menus));
        return commonResult;
    }


    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public CommonResult treeselect(SysMenu menu) {
        // 用户信息
        SysUser user = sysLoginServiceImpl.getLoginUser(ServletUtils.getRequest());
        Long userId = user.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, userId);
        return CommonResult.success(sysMenuService.buildMenuTreeSelect(menus));
    }


}
