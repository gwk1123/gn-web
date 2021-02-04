package com.gn.web.source.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.source.entity.SourcedataDataSegment;
import com.gn.web.source.service.SourcedataDataSegmentService;
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
public class SourcedataDataSegmentController {

    private final SourcedataDataSegmentService ourcedataDataSegmentService;

    public SourcedataDataSegmentController (SourcedataDataSegmentService ourcedataDataSegmentService){this.ourcedataDataSegmentService = ourcedataDataSegmentService;}

    @ApiOperation(value = "新增政策数据源行程信息")
    @PostMapping("/sourcedataDataSegment")
    public boolean saveSourcedataDataSegment(@RequestBody SourcedataDataSegment sourcedataDataSegment){
    return ourcedataDataSegmentService.saveSourcedataDataSegment(sourcedataDataSegment);
    }

    @ApiOperation(value = "删除政策数据源行程信息")
    @DeleteMapping("/sourcedataDataSegment/{id}")
    public boolean removeSourcedataDataSegment(@PathVariable("id") String id){
    return ourcedataDataSegmentService.removeSourcedataDataSegment(id);
    }

    @ApiOperation(value = "批量删除政策数据源行程信息")
    @DeleteMapping("/sourcedataDataSegments")
    public boolean removeSourcedataDataSegmentByIds(@RequestBody List <String> ids){
        return ourcedataDataSegmentService.removeSourcedataDataSegmentByIds(ids);
        }


        @ApiOperation(value = "更新政策数据源行程信息")
        @PutMapping("/sourcedataDataSegment")
        public boolean updateSourcedataDataSegment(@RequestBody SourcedataDataSegment sourcedataDataSegment){
        return ourcedataDataSegmentService.updateSourcedataDataSegment(sourcedataDataSegment);
        }

        @ApiOperation(value = "查询政策数据源行程信息分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "sourcedataDataSegment", value = "查询条件")
        })
        @GetMapping("/sourcedataDataSegment/page")
        public IPage<SourcedataDataSegment> pageSourcedataDataSegment(Page<SourcedataDataSegment> page, SourcedataDataSegment sourcedataDataSegment){
        return ourcedataDataSegmentService.pageSourcedataDataSegment(page, sourcedataDataSegment);
        }

        @ApiOperation(value = "id查询政策数据源行程信息")
        @GetMapping("/sourcedataDataSegment/{id}")
        public SourcedataDataSegment getSourcedataDataSegmentById(@PathVariable String id){
        return ourcedataDataSegmentService.getSourcedataDataSegmentById(id);
        }

        }
