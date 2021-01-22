package com.gn.web.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.constant.UserConstants;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Api(tags = {"角色信息表"})
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService ysRoleService) {
        this.sysRoleService = ysRoleService;
    }

    @ApiOperation(value = "删除角色信息表")
    @DeleteMapping("/{roleId}")
    public boolean removeSysRole(@PathVariable("roleId") String roleId) {
        return sysRoleService.removeSysRole(roleId);
    }


    @ApiOperation(value = "查询角色信息表分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数"),
            @ApiImplicitParam(name = "sysRole", value = "查询条件")
    })
    @GetMapping("/list")
    public CommonResult pageSysRole(Page<SysRole> page, SysRole sysRole) {
        IPage<SysRole> iPage =  sysRoleService.pageSysRole(page, sysRole);
        CommonResult commonResult = CommonResult.success();
        commonResult.put("total", iPage.getTotal());
        commonResult.put("rows", iPage.getRecords());
        return commonResult;
    }

    @ApiOperation(value = "id查询角色信息表")
    @GetMapping("/{roleId}")
    public CommonResult getSysRoleByRoleId(@PathVariable String roleId) {
        return CommonResult.success(sysRoleService.getSysRoleByRoleId(roleId));
    }


    /**
     * 新增角色
     * @param role
     * @return
     */
    @ApiOperation(value = "新增角色")
    @PostMapping
    public CommonResult saveSysRole(@Validated @RequestBody SysRole role)
    {
        if (UserConstants.NOT_UNIQUE.equals(sysRoleService.checkRoleNameOrRoleKeyUnique(role,"save")))
        {
            return CommonResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在或角色权限已存在");
        }
        role.setCreateBy(SecurityUtils.getUsername());
        return CommonResult.success(sysRoleService.saveSysRole(role));

    }


    /**
     * 修改保存角色
     */
    @ApiOperation(value = "修改保存角色")
    @PutMapping
    public CommonResult updateSysRole(@Validated @RequestBody SysRole role)
    {
        sysRoleService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(sysRoleService.checkRoleNameOrRoleKeyUnique(role,"update")))
        {
            return CommonResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在或角色权限已存在");
        }
        role.setUpdateBy(SecurityUtils.getUsername());

        if (sysRoleService.updateSysRole(role) > 0)
        {
            return CommonResult.success();
        }
        return CommonResult.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }


}
