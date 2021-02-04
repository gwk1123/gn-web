package com.gn.web.source.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.source.entity.SourcedataData;
import com.gn.web.source.service.SourcedataDataService;
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
@RequestMapping("/sourcedata-data")
public class SourcedataDataController {

    private final SourcedataDataService ourcedataDataService;

    public SourcedataDataController (SourcedataDataService ourcedataDataService){this.ourcedataDataService = ourcedataDataService;}

    @ApiOperation(value = "新增政策数据源")
    @PostMapping("/sourcedataData")
    public boolean saveSourcedataData(@RequestBody SourcedataData sourcedataData){
    return ourcedataDataService.saveSourcedataData(sourcedataData);
    }

    @ApiOperation(value = "删除政策数据源")
    @DeleteMapping("/sourcedataData/{id}")
    public boolean removeSourcedataData(@PathVariable("id") String id){
    return ourcedataDataService.removeSourcedataData(id);
    }

    @ApiOperation(value = "批量删除政策数据源")
    @DeleteMapping("/sourcedataDatas")
    public boolean removeSourcedataDataByIds(@RequestBody List <String> ids){
        return ourcedataDataService.removeSourcedataDataByIds(ids);
        }


        @ApiOperation(value = "更新政策数据源")
        @PutMapping("/sourcedataData")
        public boolean updateSourcedataData(@RequestBody SourcedataData sourcedataData){
        return ourcedataDataService.updateSourcedataData(sourcedataData);
        }

        @ApiOperation(value = "查询政策数据源分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "sourcedataData", value = "查询条件")
        })
        @GetMapping("/sourcedataData/page")
        public IPage<SourcedataData> pageSourcedataData(Page<SourcedataData> page, SourcedataData sourcedataData){
        return ourcedataDataService.pageSourcedataData(page, sourcedataData);
        }

        @ApiOperation(value = "id查询政策数据源")
        @GetMapping("/sourcedataData/{id}")
        public SourcedataData getSourcedataDataById(@PathVariable String id){
        return ourcedataDataService.getSourcedataDataById(id);
        }

        }
