package com.gn.web.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.constant.UserConstants;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.common.utils.StringUtils;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.service.SysRoleService;
import com.gn.web.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Api(tags = {"用户信息表"})
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    public SysUserController(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }


    @ApiOperation(value = "删除用户信息表")
    @DeleteMapping("/{userId}")
    public boolean removeSysUser(@PathVariable("userId") String userId) {
        return sysUserService.removeSysUser(userId);
    }



    @ApiOperation(value = "查询用户信息表分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数"),
            @ApiImplicitParam(name = "sysUser", value = "查询条件")
    })
    @GetMapping("/list")
    public CommonResult pageSysUser(Page<SysUser> page, SysUser sysUser) {
        IPage<SysUser> iPage = sysUserService.pageSysUser(page, sysUser);
        CommonResult commonResult = CommonResult.success();
        commonResult.put("total", iPage.getTotal());
        commonResult.put("rows", iPage.getRecords());
        return commonResult;
    }


    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation(value = "根据用户编号获取详细信息")
    @GetMapping(value = {"/", "/{userId}"})
    public CommonResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        CommonResult commonResult = CommonResult.success();
        List<SysRole> roles = sysRoleService.selectRoleAll();
        commonResult.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        if (StringUtils.isNotNull(userId)) {
            commonResult.put(CommonResult.DATA_TAG, sysUserService.getSysUserById(String.valueOf(userId)));
            commonResult.put("roleIds", sysRoleService.selectRoleListByUserId(userId));
        }
        return commonResult;
    }


    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户")
    @PostMapping
    public CommonResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(sysUserService.checkUserNameUnique(user.getUserName()))) {
            return CommonResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        sysUserService.saveSysUser(user);
        return CommonResult.success();
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户")
    @PutMapping
    public CommonResult edit(@Validated @RequestBody SysUser user) {
        sysUserService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        sysUserService.updateSysUser(user);
        return CommonResult.success();
    }


}
