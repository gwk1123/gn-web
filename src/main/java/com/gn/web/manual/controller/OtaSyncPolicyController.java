package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.manual.entity.OtaSyncPolicy;
import com.gn.web.manual.service.OtaSyncPolicyService;
import com.gn.web.source.service.SourceDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * OTA政策同步 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Api(tags = {"OTA政策同步"})
@RestController
@RequestMapping("/manual")
public class OtaSyncPolicyController {

    private final OtaSyncPolicyService taSyncPolicyService;
    @Autowired
    private SourceDataService ourcedataDataService;

    public OtaSyncPolicyController(OtaSyncPolicyService taSyncPolicyService){this.taSyncPolicyService = taSyncPolicyService;}

    @ApiOperation(value = "新增OTA政策同步")
    @PostMapping("/ota_sync_policy")
    public CommonResult saveOtaSyncPolicy(@RequestBody OtaSyncPolicy otaSyncPolicy){
    return CommonResult.success(taSyncPolicyService.saveOtaSyncPolicy(otaSyncPolicy));
    }

    @ApiOperation(value = "删除OTA政策同步")
    @DeleteMapping("/ota_sync_policy/{id}")
    public CommonResult removeOtaSyncPolicy(@PathVariable("id") String id){
    return CommonResult.success(taSyncPolicyService.removeOtaSyncPolicy(id));
    }

    @ApiOperation(value = "批量删除OTA政策同步")
    @DeleteMapping("/ota_sync_policys")
    public CommonResult removeOtaSyncPolicyByIds(@RequestBody List <String> ids){
        return CommonResult.success(taSyncPolicyService.removeOtaSyncPolicyByIds(ids));
        }


        @ApiOperation(value = "更新OTA政策同步")
        @PutMapping("/ota_sync_policy")
        public CommonResult updateOtaSyncPolicy(@RequestBody OtaSyncPolicy otaSyncPolicy){
        return CommonResult.success(taSyncPolicyService.updateOtaSyncPolicy(otaSyncPolicy));
        }

        @ApiOperation(value = "查询OTA政策同步分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "otaSyncPolicy", value = "查询条件")
        })
        @GetMapping("/ota_sync_policy/page")
        public CommonResult pageOtaSyncPolicy(Page<OtaSyncPolicy> page, OtaSyncPolicy otaSyncPolicy){
            ourcedataDataService.sourceDataCache();
        return CommonResult.success(taSyncPolicyService.pageOtaSyncPolicy(page, otaSyncPolicy));
        }

        @ApiOperation(value = "id查询OTA政策同步")
        @GetMapping("/ota_sync_policy/{id}")
        public CommonResult getOtaSyncPolicyById(@PathVariable String id){
        return CommonResult.success(taSyncPolicyService.getOtaSyncPolicyById(id));
        }

        }
