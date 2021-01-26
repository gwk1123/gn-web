package com.gn.web.direct.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.common.utils.CommonResult;
import com.gn.web.direct.entity.BaseAirRoute;
import com.gn.web.direct.service.BaseAirRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 政策基础航司航线 前端控制器
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Api(tags = {"政策基础航司航线"})
@RestController
@RequestMapping("/direct")
public class BaseAirRouteController {

    private final BaseAirRouteService aseAirRouteService;

    public BaseAirRouteController (BaseAirRouteService aseAirRouteService){this.aseAirRouteService = aseAirRouteService;}

    @ApiOperation(value = "新增政策基础航司航线")
    @PostMapping("/base_air_route")
    public CommonResult saveBaseAirRoute(@RequestBody BaseAirRoute baseAirRoute){
    return CommonResult.success(aseAirRouteService.saveBaseAirRoute(baseAirRoute));
    }

    @ApiOperation(value = "删除政策基础航司航线")
    @DeleteMapping("/base_air_route/{id}")
    public CommonResult removeBaseAirRoute(@PathVariable("id") String id){
    return CommonResult.success(aseAirRouteService.removeBaseAirRoute(id));
    }

    @ApiOperation(value = "批量删除政策基础航司航线")
    @DeleteMapping("/base_air_routes")
    public CommonResult removeBaseAirRouteByIds(@RequestBody List <String> ids){
        return CommonResult.success(aseAirRouteService.removeBaseAirRouteByIds(ids));
        }


        @ApiOperation(value = "更新政策基础航司航线")
        @PutMapping("/base_air_route")
        public CommonResult updateBaseAirRoute(@RequestBody BaseAirRoute baseAirRoute){
        return CommonResult.success(aseAirRouteService.updateBaseAirRoute(baseAirRoute));
        }

        @ApiOperation(value = "查询政策基础航司航线分页数据")
        @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "分页参数"),
        @ApiImplicitParam(name = "baseAirRoute", value = "查询条件")
        })
        @GetMapping("/base_air_route/page")
        public CommonResult pageBaseAirRoute(Page<BaseAirRoute> page, BaseAirRoute baseAirRoute){
        return CommonResult.success(aseAirRouteService.pageBaseAirRoute(page, baseAirRoute));
        }

        @ApiOperation(value = "id查询政策基础航司航线")
        @GetMapping("/base_air_route/{id}")
        public CommonResult getBaseAirRouteById(@PathVariable String id){
        return CommonResult.success(aseAirRouteService.getBaseAirRouteById(id));
        }

        }
