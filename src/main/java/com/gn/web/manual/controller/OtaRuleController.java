package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.manual.service.OtaRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * OTA规则 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-01-28
 */
@Api(tags = {"OTA规则"})
@RestController
@RequestMapping("/manual")
public class OtaRuleController {

    private final OtaRuleService taRuleService;

    public OtaRuleController(OtaRuleService taRuleService) {
        this.taRuleService = taRuleService;
    }

    @ApiOperation(value = "新增OTA规则")
    @PostMapping("/ota_rule")
    public CommonResult saveOtaRule(@RequestBody OtaRule otaRule) {
        return CommonResult.success(taRuleService.saveOtaRule(otaRule));
    }

    @ApiOperation(value = "删除OTA规则")
    @DeleteMapping("/ota_rule/{id}")
    public CommonResult removeOtaRule(@PathVariable("id") String id) {
        return CommonResult.success(taRuleService.removeOtaRule(id));
    }

    @ApiOperation(value = "批量删除OTA规则")
    @DeleteMapping("/ota_rules")
    public CommonResult removeOtaRuleByIds(@RequestBody List<String> ids) {
        return CommonResult.success(taRuleService.removeOtaRuleByIds(ids));
    }


    @ApiOperation(value = "更新OTA规则")
    @PutMapping("/ota_rule")
    public CommonResult updateOtaRule(@RequestBody OtaRule otaRule) {
        return CommonResult.success(taRuleService.updateOtaRule(otaRule));
    }

    @ApiOperation(value = "查询OTA规则分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数"),
            @ApiImplicitParam(name = "otaRule", value = "查询条件")
    })
    @GetMapping("/ota_rule/page")
    public CommonResult pageOtaRule(Page<OtaRule> page, OtaRule otaRule) {
        return CommonResult.success(taRuleService.pageOtaRule(page, otaRule));
    }

    @ApiOperation(value = "id查询OTA规则")
    @GetMapping("/ota_rule/{id}")
    public CommonResult getOtaRuleById(@PathVariable String id) {
        return CommonResult.success(taRuleService.getOtaRuleById(id));
    }

    @ApiOperation(value = "更新OTA规则状态")
    @PutMapping("/ota_rule/change_status")
    public CommonResult changeStatus(@RequestBody OtaRule otaRule) {
        return CommonResult.success(taRuleService.changeStatus(otaRule));
    }


}
