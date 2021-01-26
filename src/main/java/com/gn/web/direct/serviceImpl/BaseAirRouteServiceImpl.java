package com.gn.web.direct.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.direct.entity.BaseAirRoute;
import com.gn.web.direct.mapper.BaseAirRouteMapper;
import com.gn.web.direct.service.BaseAirRouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 政策基础航司航线 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Service
public class BaseAirRouteServiceImpl extends ServiceImpl<BaseAirRouteMapper, BaseAirRoute> implements BaseAirRouteService {

    @Override
    public IPage<BaseAirRoute> pageBaseAirRoute(Page<BaseAirRoute> page, BaseAirRoute baseAirRoute){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<BaseAirRoute> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBaseAirRoute(BaseAirRoute baseAirRoute){
        Assert.notNull(baseAirRoute, "政策基础航司航线为空");
        return this.save(baseAirRoute);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBaseAirRoute(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBaseAirRouteByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBaseAirRoute(BaseAirRoute baseAirRoute){
        Assert.notNull(baseAirRoute, "政策基础航司航线为空");
        return this.updateById(baseAirRoute);
    }

    @Override
    public BaseAirRoute getBaseAirRouteById(String id){
        return  this.getById(id);
    }
}
