package com.gn.web.direct.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.direct.entity.BaseCabin;
import com.gn.web.direct.service.BaseCabinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 政策基础舱位 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Api(tags = {"政策基础舱位"})
@RestController
@RequestMapping("/direct")
public class BaseCabinController {

    private final BaseCabinService aseCabinService;

    public BaseCabinController (BaseCabinService aseCabinService){this.aseCabinService = aseCabinService;}

    @ApiOperation(value = "新增政策基础舱位")
    @PostMapping("/base_cabin")
    public CommonResult saveBaseCabin(@RequestBody BaseCabin baseCabin){
    return CommonResult.success(aseCabinService.saveBaseCabin(baseCabin));
    }

    @ApiOperation(value = "删除政策基础舱位")
    @DeleteMapping("/base_cabin/{id}")
    public CommonResult removeBaseCabin(@PathVariable("id") String id){
    return CommonResult.success(aseCabinService.removeBaseCabin(id));
    }

    @ApiOperation(value = "批量删除政策基础舱位")
    @DeleteMapping("/base_cabins")
    public CommonResult removeBaseCabinByIds(@RequestBody List <String> ids){
        return CommonResult.success(aseCabinService.removeBaseCabinByIds(ids));
        }


        @ApiOperation(value = "更新政策基础舱位")
        @PutMapping("/base_cabin")
        public CommonResult updateBaseCabin(@RequestBody BaseCabin baseCabin){
        return CommonResult.success(aseCabinService.updateBaseCabin(baseCabin));
        }

        @ApiOperation(value = "查询政策基础舱位分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "baseCabin", value = "查询条件")
        })
        @GetMapping("/base_cabin/page")
        public CommonResult pageBaseCabin(Page<BaseCabin> page, BaseCabin baseCabin){
        return CommonResult.success(aseCabinService.pageBaseCabin(page, baseCabin));
        }

        @ApiOperation(value = "id查询政策基础舱位")
        @GetMapping("/base_cabin/{id}")
        public CommonResult getBaseCabinById(@PathVariable String id){
        return CommonResult.success(aseCabinService.getBaseCabinById(id));
        }

        }
