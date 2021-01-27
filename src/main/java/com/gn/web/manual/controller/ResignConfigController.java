package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.manual.entity.ResignConfig;
import com.gn.web.manual.service.ResignConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gwk
 * @since 2020-11-17
 */
@Api(tags = {"退改签"})
@RestController
@RequestMapping("/manual")
public class ResignConfigController {

    private final ResignConfigService esignConfigService;

    public ResignConfigController(ResignConfigService esignConfigService){this.esignConfigService = esignConfigService;}

    @ApiOperation(value = "新增")
    @PostMapping("/resign_config")
    public CommonResult saveResignConfig(@RequestBody ResignConfig resignConfig){
    return CommonResult.success(esignConfigService.saveResignConfig(resignConfig));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/resign_config/{id}")
    public CommonResult removeResignConfig(@PathVariable("id") String id){
    return CommonResult.success(esignConfigService.removeResignConfig(id));
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/resign_configs")
    public CommonResult removeResignConfigByIds(@RequestBody List <String> ids){
        return CommonResult.success(esignConfigService.removeResignConfigByIds(ids));
        }


        @ApiOperation(value = "更新")
        @PutMapping("/resign_config")
        public CommonResult updateResignConfig(@RequestBody ResignConfig resignConfig){
        return CommonResult.success(esignConfigService.updateResignConfig(resignConfig));
        }

        @ApiOperation(value = "查询分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "resignConfig", value = "查询条件")
        })
        @GetMapping("/resign_config/page")
        public CommonResult pageResignConfig(Page<ResignConfig> page,ResignConfig resignConfig){
        return CommonResult.success(esignConfigService.pageResignConfig(page, resignConfig));
        }

        @ApiOperation(value = "id查询")
        @GetMapping("/resign_config/{id}")
        public CommonResult getResignConfigById(@PathVariable String id){
        return CommonResult.success(esignConfigService.getResignConfigById(id));
        }

        }
