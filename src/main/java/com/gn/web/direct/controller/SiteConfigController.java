package com.gn.web.direct.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.direct.entity.SiteConfig;
import com.gn.web.direct.service.SiteConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 政策站点配置 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-01-26
 */
@Api(tags = {"政策站点配置"})
@RestController
@RequestMapping("/direct")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    public SiteConfigController(SiteConfigService iteConfigService){this.siteConfigService = iteConfigService;}

    @ApiOperation(value = "新增政策站点配置")
    @PostMapping("/site_config")
    public CommonResult saveSiteConfig(@RequestBody SiteConfig siteConfig){
    return CommonResult.success(siteConfigService.saveSiteConfig(siteConfig));
    }

    @ApiOperation(value = "删除政策站点配置")
    @DeleteMapping("/site_config/{id}")
    public CommonResult removeSiteConfig(@PathVariable("id") String id){
    return CommonResult.success(siteConfigService.removeSiteConfig(id));
    }

    @ApiOperation(value = "批量删除政策站点配置")
    @DeleteMapping("/site_config")
    public CommonResult removeSiteConfigByIds(@RequestBody List <String> ids){
        return CommonResult.success(siteConfigService.removeSiteConfigByIds(ids));
        }


        @ApiOperation(value = "更新政策站点配置")
        @PutMapping("/site_config")
        public CommonResult updateSiteConfig(@RequestBody SiteConfig siteConfig){
        return CommonResult.success(siteConfigService.updateSiteConfig(siteConfig));
        }

        @ApiOperation(value = "查询政策站点配置分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "siteConfig", value = "查询条件")
        })
        @GetMapping("/site_config/page")
        public CommonResult pageSiteConfig(Page<SiteConfig> page, SiteConfig siteConfig){
        return CommonResult.success(siteConfigService.pageSiteConfig(page, siteConfig));
        }

        @ApiOperation(value = "id查询政策站点配置")
        @GetMapping("/site_config/{id}")
        public CommonResult getSiteConfigById(@PathVariable String id){
        return CommonResult.success(siteConfigService.getSiteConfigById(id));
        }

        }
