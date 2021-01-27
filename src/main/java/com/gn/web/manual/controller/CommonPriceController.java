package com.gn.web.manual.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.manual.entity.CommonPrice;
import com.gn.web.manual.service.CommonPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 全平台调价 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Api(tags = {"全平台调价"})
@RestController
@RequestMapping("/manual")
public class CommonPriceController {

    private final CommonPriceService ommonPriceService;

    public CommonPriceController(CommonPriceService ommonPriceService){this.ommonPriceService = ommonPriceService;}

    @ApiOperation(value = "新增全平台调价")
    @PostMapping("/common_price")
    public CommonResult saveCommonPrice(@RequestBody CommonPrice commonPrice){
    return CommonResult.success(ommonPriceService.saveCommonPrice(commonPrice));
    }

    @ApiOperation(value = "删除全平台调价")
    @DeleteMapping("/common_price/{id}")
    public CommonResult removeCommonPrice(@PathVariable("id") String id){
    return CommonResult.success(ommonPriceService.removeCommonPrice(id));
    }

    @ApiOperation(value = "批量删除全平台调价")
    @DeleteMapping("/common_prices")
    public CommonResult removeCommonPriceByIds(@RequestBody List <String> ids){
        return CommonResult.success(ommonPriceService.removeCommonPriceByIds(ids));
        }


        @ApiOperation(value = "更新全平台调价")
        @PutMapping("/common_price")
        public CommonResult updateCommonPrice(@RequestBody CommonPrice commonPrice){
        return CommonResult.success(ommonPriceService.updateCommonPrice(commonPrice));
        }

        @ApiOperation(value = "查询全平台调价分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "commonPrice", value = "查询条件")
        })
        @GetMapping("/common_price/page")
        public CommonResult pageCommonPrice(Page<CommonPrice> page, CommonPrice commonPrice){
        return CommonResult.success(ommonPriceService.pageCommonPrice(page, commonPrice));
        }

        @ApiOperation(value = "id查询全平台调价")
        @GetMapping("/common_price/{id}")
        public CommonResult getCommonPriceById(@PathVariable String id){
        return CommonResult.success(ommonPriceService.getCommonPriceById(id));
        }

        }
