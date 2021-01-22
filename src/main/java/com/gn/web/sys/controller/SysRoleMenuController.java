package com.gn.web.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.sys.entity.SysRoleMenu;
import com.gn.web.sys.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 前端控制器
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Api(tags = {"角色和菜单关联表"})
@RestController
@RequestMapping("/sys/sys-role-menu")
public class SysRoleMenuController {

    private final SysRoleMenuService ysRoleMenuService;

    public SysRoleMenuController (SysRoleMenuService ysRoleMenuService){this.ysRoleMenuService = ysRoleMenuService;}

    @ApiOperation(value = "新增角色和菜单关联表")
    @PostMapping("/sysRoleMenu")
    public boolean saveSysRoleMenu(@RequestBody SysRoleMenu sysRoleMenu){
    return ysRoleMenuService.saveSysRoleMenu(sysRoleMenu);
    }

    @ApiOperation(value = "删除角色和菜单关联表")
    @DeleteMapping("/sysRoleMenu/{id}")
    public boolean removeSysRoleMenu(@PathVariable("id") String id){
    return ysRoleMenuService.removeSysRoleMenu(id);
    }

    @ApiOperation(value = "批量删除角色和菜单关联表")
    @DeleteMapping("/sysRoleMenus")
    public boolean removeSysRoleMenuByIds(@RequestBody List <String> ids){
        return ysRoleMenuService.removeSysRoleMenuByIds(ids);
        }


        @ApiOperation(value = "更新角色和菜单关联表")
        @PutMapping("/sysRoleMenu")
        public boolean updateSysRoleMenu(@RequestBody SysRoleMenu sysRoleMenu){
        return ysRoleMenuService.updateSysRoleMenu(sysRoleMenu);
        }

        @ApiOperation(value = "查询角色和菜单关联表分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "sysRoleMenu", value = "查询条件")
        })
        @GetMapping("/sysRoleMenu/page")
        public IPage<SysRoleMenu> pageSysRoleMenu(Page<SysRoleMenu> page,SysRoleMenu sysRoleMenu){
        return ysRoleMenuService.pageSysRoleMenu(page, sysRoleMenu);
        }

        @ApiOperation(value = "id查询角色和菜单关联表")
        @GetMapping("/sysRoleMenu/{id}")
        public SysRoleMenu getSysRoleMenuById(@PathVariable String id){
        return ysRoleMenuService.getSysRoleMenuById(id);
        }

        }
