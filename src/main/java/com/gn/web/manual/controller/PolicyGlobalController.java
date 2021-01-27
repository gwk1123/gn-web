package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.manual.entity.PolicyGlobal;
import com.gn.web.manual.service.PolicyGlobalService;
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
public class PolicyGlobalController {

    private final PolicyGlobalService olicyGlobalService;

    public PolicyGlobalController (PolicyGlobalService olicyGlobalService){this.olicyGlobalService = olicyGlobalService;}

    @ApiOperation(value = "新增全平台政策管理")
    @PostMapping("/policy_global")
    public boolean savePolicyGlobal(@RequestBody PolicyGlobal policyGlobal){
    return olicyGlobalService.savePolicyGlobal(policyGlobal);
    }

    @ApiOperation(value = "删除全平台政策管理")
    @DeleteMapping("/policy_global/{id}")
    public boolean removePolicyGlobal(@PathVariable("id") String id){
    return olicyGlobalService.removePolicyGlobal(id);
    }

    @ApiOperation(value = "批量删除全平台政策管理")
    @DeleteMapping("/policy_globals")
    public boolean removePolicyGlobalByIds(@RequestBody List <String> ids){
        return olicyGlobalService.removePolicyGlobalByIds(ids);
        }


        @ApiOperation(value = "更新全平台政策管理")
        @PutMapping("/policy_global")
        public boolean updatePolicyGlobal(@RequestBody PolicyGlobal policyGlobal){
        return olicyGlobalService.updatePolicyGlobal(policyGlobal);
        }

        @ApiOperation(value = "查询全平台政策管理分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "policyGlobal", value = "查询条件")
        })
        @GetMapping("/policy_global/page")
        public IPage<PolicyGlobal> pagePolicyGlobal(Page<PolicyGlobal> page, PolicyGlobal policyGlobal){
        return olicyGlobalService.pagePolicyGlobal(page, policyGlobal);
        }

        @ApiOperation(value = "id查询全平台政策管理")
        @GetMapping("/policy_global/{id}")
        public PolicyGlobal getPolicyGlobalById(@PathVariable String id){
        return olicyGlobalService.getPolicyGlobalById(id);
        }

        }
