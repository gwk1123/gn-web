package com.gn.web.source.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.source.entity.SourceDataSegment;
import com.gn.web.source.service.SourceDataSegmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 政策数据源行程信息 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Api(tags = {"政策数据源行程信息"})
@RestController
@RequestMapping("/sourcedata-data-segment")
public class SourceDataSegmentController {

    private final SourceDataSegmentService ourcedataDataSegmentService;

    public SourceDataSegmentController (SourceDataSegmentService ourcedataDataSegmentService){this.ourcedataDataSegmentService = ourcedataDataSegmentService;}

    @ApiOperation(value = "新增政策数据源行程信息")
    @PostMapping("/SourceDataSegment")
    public boolean saveSourceDataSegment(@RequestBody SourceDataSegment SourceDataSegment){
    return ourcedataDataSegmentService.saveSourceDataSegment(SourceDataSegment);
    }

    @ApiOperation(value = "删除政策数据源行程信息")
    @DeleteMapping("/SourceDataSegment/{id}")
    public boolean removeSourceDataSegment(@PathVariable("id") String id){
    return ourcedataDataSegmentService.removeSourceDataSegment(id);
    }

    @ApiOperation(value = "批量删除政策数据源行程信息")
    @DeleteMapping("/SourceDataSegments")
    public boolean removeSourceDataSegmentByIds(@RequestBody List <String> ids){
        return ourcedataDataSegmentService.removeSourceDataSegmentByIds(ids);
        }


        @ApiOperation(value = "更新政策数据源行程信息")
        @PutMapping("/SourceDataSegment")
        public boolean updateSourceDataSegment(@RequestBody SourceDataSegment SourceDataSegment){
        return ourcedataDataSegmentService.updateSourceDataSegment(SourceDataSegment);
        }

        @ApiOperation(value = "查询政策数据源行程信息分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "SourceDataSegment", value = "查询条件")
        })
        @GetMapping("/SourceDataSegment/page")
        public IPage<SourceDataSegment> pageSourceDataSegment(Page<SourceDataSegment> page, SourceDataSegment SourceDataSegment){
        return ourcedataDataSegmentService.pageSourceDataSegment(page, SourceDataSegment);
        }

        @ApiOperation(value = "id查询政策数据源行程信息")
        @GetMapping("/SourceDataSegment/{id}")
        public SourceDataSegment getSourceDataSegmentById(@PathVariable String id){
        return ourcedataDataSegmentService.getSourceDataSegmentById(id);
        }

        }
