package com.gn.web.source.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.source.entity.SourceData;
import com.gn.web.source.mapper.SourceDataMapper;
import com.gn.web.source.service.SourceDataService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Optional;
import org.springframework.util.CollectionUtils;


/**
 * <p>
 * 政策数据源 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-02-05
 */
@Service
public class SourceDataServiceImpl extends ServiceImpl<SourceDataMapper, SourceData> implements SourceDataService {

    @Override
    public  IPage<SourceData> pageSourceData(Page<SourceData> page, SourceData sourceData){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SourceData> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSourceData(SourceData sourceData){
        Assert.notNull(sourceData, "政策数据源为空");
        return this.save(sourceData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourceData(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourceDataByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSourceData(SourceData sourceData){
        Assert.notNull(sourceData, "政策数据源为空");
        return this.updateById(sourceData);
    }

    @Override
    public SourceData getSourceDataById(String id){
        return  this.getById(id);
    }
}
