package com.gn.web.source.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.source.entity.SourcedataData;
import com.gn.web.source.mapper.SourcedataDataMapper;
import com.gn.web.source.service.SourcedataDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 政策数据源 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Service
public class SourcedataDataServiceImpl extends ServiceImpl<SourcedataDataMapper, SourcedataData> implements SourcedataDataService {

    @Override
    public IPage<SourcedataData> pageSourcedataData(Page<SourcedataData> page, SourcedataData sourcedataData){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SourcedataData> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSourcedataData(SourcedataData sourcedataData){
        Assert.notNull(sourcedataData, "政策数据源为空");
        return this.save(sourcedataData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourcedataData(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourcedataDataByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSourcedataData(SourcedataData sourcedataData){
        Assert.notNull(sourcedataData, "政策数据源为空");
        return this.updateById(sourcedataData);
    }

    @Override
    public SourcedataData getSourcedataDataById(String id){
        return  this.getById(id);
    }
}
