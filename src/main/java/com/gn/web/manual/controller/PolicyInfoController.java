package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.manual.entity.PolicyInfo;
import com.gn.web.manual.service.PolicyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 全平台政策管理 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
@Api(tags = {"全平台政策管理"})
@RestController
@RequestMapping("/manual")
public class PolicyInfoController {

    private final PolicyInfoService olicyInfoService;

    public PolicyInfoController (PolicyInfoService olicyInfoService){this.olicyInfoService = olicyInfoService;}

    @ApiOperation(value = "新增全平台政策管理")
    @PostMapping("/policy_info")
    public CommonResult savePolicyInfo(@RequestBody PolicyInfo policyInfo){
    return CommonResult.success(olicyInfoService.savePolicyInfo(policyInfo));
    }

    @ApiOperation(value = "删除全平台政策管理")
    @DeleteMapping("/policy_info/{id}")
    public CommonResult removePolicyInfo(@PathVariable("id") String id){
    return CommonResult.success(olicyInfoService.removePolicyInfo(id));
    }

    @ApiOperation(value = "批量删除全平台政策管理")
    @DeleteMapping("/policy_infos")
    public CommonResult removePolicyInfoByIds(@RequestBody List <String> ids){
        return CommonResult.success(olicyInfoService.removePolicyInfoByIds(ids));
        }


        @ApiOperation(value = "更新全平台政策管理")
        @PutMapping("/policy_info")
        public CommonResult updatePolicyInfo(@RequestBody PolicyInfo policyInfo){
        return CommonResult.success(olicyInfoService.updatePolicyInfo(policyInfo));
        }

        @ApiOperation(value = "查询全平台政策管理分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "policyInfo", value = "查询条件")
        })
        @GetMapping("/policy_info/page")
        public CommonResult pagePolicyInfo(Page<PolicyInfo> page, PolicyInfo policyInfo){
        return CommonResult.success(olicyInfoService.pagePolicyInfo(page, policyInfo));
        }

        @ApiOperation(value = "id查询全平台政策管理")
        @GetMapping("/policy_info/{id}")
        public CommonResult getPolicyInfoById(@PathVariable String id){
        return CommonResult.success(olicyInfoService.getPolicyInfoById(id));
        }

        }
