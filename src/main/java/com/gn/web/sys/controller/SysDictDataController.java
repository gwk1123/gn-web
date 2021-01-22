package com.gn.web.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.sys.entity.SysDictData;
import com.gn.web.sys.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Api(tags = {"字典数据表"})
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    public SysDictDataController(SysDictDataService sysDictDataService) {
        this.sysDictDataService = sysDictDataService;
    }

    @ApiOperation(value = "新增字典数据表")
    @PostMapping()
    public CommonResult saveSysDictData(@RequestBody SysDictData sysDictData) {
        sysDictData.setUpdateBy(SecurityUtils.getUsername());
        sysDictData.setCreateBy(SecurityUtils.getUsername());
        sysDictData.setCreateTime(LocalDateTime.now());
        sysDictData.setUpdateTime(LocalDateTime.now());
        return CommonResult.success(sysDictDataService.saveSysDictData(sysDictData));
    }

    @ApiOperation(value = "删除字典数据表")
    @DeleteMapping("/{dictCode}")
    public CommonResult removeSysDictData(@PathVariable("dictCode") String dictCode) {
        return CommonResult.success(sysDictDataService.removeSysDictData(dictCode));
    }


    @ApiOperation(value = "更新字典数据表")
    @PutMapping()
    public CommonResult updateSysDictData(@RequestBody SysDictData sysDictData) {
        sysDictData.setUpdateBy(SecurityUtils.getUsername());
        sysDictData.setUpdateTime(LocalDateTime.now());
        return CommonResult.success(sysDictDataService.updateSysDictData(sysDictData));
    }

    @ApiOperation(value = "id查询字典数据表")
    @GetMapping("/{dictCode}")
    public CommonResult getSysDictDataById(@PathVariable String dictCode) {
        return CommonResult.success(sysDictDataService.getSysDictDataById(dictCode));
    }


    @ApiOperation(value = "查询字典数据表分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数"),
            @ApiImplicitParam(name = "sysDictData", value = "查询条件")
    })
    @GetMapping("/list")
    public CommonResult list(Page<SysDictData> page,SysDictData sysDictData)
    {
        IPage<SysDictData> iPage= sysDictDataService.listDictDataList(page,sysDictData);
        CommonResult commonResult = CommonResult.success();
        commonResult.put("total", iPage.getTotal());
        commonResult.put("rows", iPage.getRecords());
        return commonResult;
    }



    /**
     * 根据字典类型查询字典数据信息(集合)
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/type/{dictType}")
    public CommonResult getDictType(@PathVariable String dictType)
    {
        return CommonResult.success(sysDictDataService.getDictType(dictType));
    }


    /**
     * 根据字典类型查询字典数据信息(单个对象)
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/SysDictData/{dictType}")
    public CommonResult getDictTypeBySysDictData(@PathVariable String dictType)
    {
        return CommonResult.success(sysDictDataService.getDictTypeBySysDictData(dictType));
    }

}
