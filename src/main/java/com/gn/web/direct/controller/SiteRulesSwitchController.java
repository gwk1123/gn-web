package com.gn.web.direct.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.direct.entity.SiteRulesSwitch;
import com.gn.web.direct.service.SiteRulesSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 站点规则开关 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2020-09-05
 */
@Api(tags = {"站点规则开关"})
@RestController
@RequestMapping("/direct")
public class SiteRulesSwitchController {

    private final SiteRulesSwitchService iteRulesSwitchService;

    public SiteRulesSwitchController(SiteRulesSwitchService iteRulesSwitchService){this.iteRulesSwitchService = iteRulesSwitchService;}

    @ApiOperation(value = "新增站点规则开关")
    @PostMapping("/site_rules_switch")
    public CommonResult saveSiteRulesSwitch(@RequestBody SiteRulesSwitch siteRulesSwitch){
    return CommonResult.success(iteRulesSwitchService.saveSiteRulesSwitch(siteRulesSwitch));
    }

    @ApiOperation(value = "删除站点规则开关")
    @DeleteMapping("/site_rules_switch/{id}")
    public CommonResult removeSiteRulesSwitch(@PathVariable("id") String id){
    return CommonResult.success(iteRulesSwitchService.removeSiteRulesSwitch(id));
    }

    @ApiOperation(value = "批量删除站点规则开关")
    @DeleteMapping("/site_rules_switch")
    public CommonResult removeSiteRulesSwitchByIds(@RequestBody List <String> ids){
        return CommonResult.success(iteRulesSwitchService.removeSiteRulesSwitchByIds(ids));
        }


        @ApiOperation(value = "更新站点规则开关")
        @PutMapping("/site_rules_switch")
        public CommonResult updateSiteRulesSwitch(@RequestBody SiteRulesSwitch siteRulesSwitch){
        return CommonResult.success(iteRulesSwitchService.updateSiteRulesSwitch(siteRulesSwitch));
        }

        @ApiOperation(value = "查询站点规则开关分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "siteRulesSwitch", value = "查询条件")
        })
        @GetMapping("/site_rules_switch/page")
        public CommonResult pageSiteRulesSwitch(Page<SiteRulesSwitch> page,SiteRulesSwitch siteRulesSwitch){
            IPage<SiteRulesSwitch> iPage = iteRulesSwitchService.pageSiteRulesSwitch(page, siteRulesSwitch);
            return CommonResult.success(iPage);
        }

        @ApiOperation(value = "id查询站点规则开关")
        @GetMapping("/site_rules_switch/{id}")
        public CommonResult getSiteRulesSwitchById(@PathVariable String id){
        return CommonResult.success(iteRulesSwitchService.getSiteRulesSwitchById(id));
        }

        }
