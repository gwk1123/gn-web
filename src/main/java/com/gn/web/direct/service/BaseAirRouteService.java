package com.gn.web.direct.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.direct.entity.BaseAirRoute;
import java.util.List;

/**
 * <p>
 * 政策基础航司航线 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
public interface BaseAirRouteService extends IService<BaseAirRoute> {

    /**
     * 查询政策基础航司航线分页数据
     *
     * @param page      分页参数
     * @param baseAirRoute 查询条件
     * @return IPage<BaseAirRoute>
     */
     IPage<BaseAirRoute> pageBaseAirRoute(Page<BaseAirRoute> page, BaseAirRoute baseAirRoute);

    /**
     * 新增政策基础航司航线
     *
     * @param baseAirRoute 政策基础航司航线
     * @return boolean
     */
    boolean saveBaseAirRoute(BaseAirRoute baseAirRoute);

    /**
     * 删除政策基础航司航线
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeBaseAirRoute(String id);

    /**
     * 批量删除政策基础航司航线
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeBaseAirRouteByIds(List<String> ids);

    /**
     * 修改政策基础航司航线
     *
     * @param baseAirRoute 政策基础航司航线
     * @return boolean
     */
    boolean updateBaseAirRoute(BaseAirRoute baseAirRoute);

    /**
     * id查询数据
     *
     * @param id id
     * @return BaseAirRoute
     */
    BaseAirRoute getBaseAirRouteById(String id);
}
