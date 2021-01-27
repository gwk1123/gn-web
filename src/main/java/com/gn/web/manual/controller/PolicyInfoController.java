package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public boolean savePolicyInfo(@RequestBody PolicyInfo policyInfo){
    return olicyInfoService.savePolicyInfo(policyInfo);
    }

    @ApiOperation(value = "删除全平台政策管理")
    @DeleteMapping("/policy_info/{id}")
    public boolean removePolicyInfo(@PathVariable("id") String id){
    return olicyInfoService.removePolicyInfo(id);
    }

    @ApiOperation(value = "批量删除全平台政策管理")
    @DeleteMapping("/policy_infos")
    public boolean removePolicyInfoByIds(@RequestBody List <String> ids){
        return olicyInfoService.removePolicyInfoByIds(ids);
        }


        @ApiOperation(value = "更新全平台政策管理")
        @PutMapping("/policy_info")
        public boolean updatePolicyInfo(@RequestBody PolicyInfo policyInfo){
        return olicyInfoService.updatePolicyInfo(policyInfo);
        }

        @ApiOperation(value = "查询全平台政策管理分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "policyInfo", value = "查询条件")
        })
        @GetMapping("/policy_info/page")
        public IPage<PolicyInfo> pagePolicyInfo(Page<PolicyInfo> page, PolicyInfo policyInfo){
        return olicyInfoService.pagePolicyInfo(page, policyInfo);
        }

        @ApiOperation(value = "id查询全平台政策管理")
        @GetMapping("/policy_info/{id}")
        public PolicyInfo getPolicyInfoById(@PathVariable String id){
        return olicyInfoService.getPolicyInfoById(id);
        }

        }
