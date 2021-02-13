package com.gn.web.source.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.source.entity.SourceData;
import com.gn.web.source.service.SourceDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 政策数据源 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Api(tags = {"政策数据源"})
@RestController
@RequestMapping("/source_data")
public class SourceDataController {

    private final SourceDataService ourcedataDataService;

    public SourceDataController (SourceDataService ourcedataDataService){this.ourcedataDataService = ourcedataDataService;}

    @ApiOperation(value = "新增政策数据源")
    @PostMapping("/SourceData")
    public boolean saveSourceData(@RequestBody SourceData SourceData){
    return ourcedataDataService.saveSourceData(SourceData);
    }

    @ApiOperation(value = "删除政策数据源")
    @DeleteMapping("/SourceData/{id}")
    public boolean removeSourceData(@PathVariable("id") String id){
    return ourcedataDataService.removeSourceData(id);
    }

    @ApiOperation(value = "批量删除政策数据源")
    @DeleteMapping("/SourceDatas")
    public boolean removeSourceDataByIds(@RequestBody List <String> ids){
        return ourcedataDataService.removeSourceDataByIds(ids);
        }


        @ApiOperation(value = "更新政策数据源")
        @PutMapping("/SourceData")
        public boolean updateSourceData(@RequestBody SourceData SourceData){
        return ourcedataDataService.updateSourceData(SourceData);
        }

        @ApiOperation(value = "查询政策数据源分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "SourceData", value = "查询条件")
        })
        @GetMapping("/SourceData/page")
        public IPage<SourceData> pageSourceData(Page<SourceData> page, SourceData SourceData){
        return ourcedataDataService.pageSourceData(page, SourceData);
        }

        @ApiOperation(value = "id查询政策数据源")
        @GetMapping("/SourceData/{id}")
        public SourceData getSourceDataById(@PathVariable String id){
        return ourcedataDataService.getSourceDataById(id);
        }

        }
