package com.gn.web.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.sys.entity.SysUserRole;
import com.gn.web.sys.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户和角色关联表 前端控制器
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Api(tags = {"用户和角色关联表"})
@RestController
@RequestMapping("/sys/sys-user-role")
public class SysUserRoleController {

    private final SysUserRoleService ysUserRoleService;

    public SysUserRoleController(SysUserRoleService ysUserRoleService) {
        this.ysUserRoleService = ysUserRoleService;
    }

    @ApiOperation(value = "新增用户和角色关联表")
    @PostMapping("/sysUserRole")
    public boolean saveSysUserRole(@RequestBody SysUserRole sysUserRole) {
        return ysUserRoleService.saveSysUserRole(sysUserRole);
    }

    @ApiOperation(value = "删除用户和角色关联表")
    @DeleteMapping("/sysUserRole/{id}")
    public boolean removeSysUserRole(@PathVariable("id") String id) {
        return ysUserRoleService.removeSysUserRole(id);
    }


    @ApiOperation(value = "查询用户和角色关联表分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数"),
            @ApiImplicitParam(name = "sysUserRole", value = "查询条件")
    })
    @GetMapping("/sysUserRole/page")
    public IPage<SysUserRole> pageSysUserRole(Page<SysUserRole> page, SysUserRole sysUserRole) {
        return ysUserRoleService.pageSysUserRole(page, sysUserRole);
    }

    @ApiOperation(value = "id查询用户和角色关联表")
    @GetMapping("/sysUserRole/{id}")
    public SysUserRole getSysUserRoleById(@PathVariable String id) {
        return ysUserRoleService.getSysUserRoleById(id);
    }

}
