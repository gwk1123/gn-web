package com.gn.web.source.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.source.entity.SourceDataSegment;
import com.gn.web.source.mapper.SourceDataSegmentMapper;
import com.gn.web.source.service.SourceDataSegmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 政策数据源行程信息 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
@Service
public class SourceDataSegmentServiceImpl extends ServiceImpl<SourceDataSegmentMapper, SourceDataSegment> implements SourceDataSegmentService {

    @Override
    public IPage<SourceDataSegment> pageSourceDataSegment(Page<SourceDataSegment> page, SourceDataSegment SourceDataSegment){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SourceDataSegment> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSourceDataSegment(SourceDataSegment SourceDataSegment){
        Assert.notNull(SourceDataSegment, "政策数据源行程信息为空");
        return this.save(SourceDataSegment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourceDataSegment(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourceDataSegmentByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSourceDataSegment(SourceDataSegment SourceDataSegment){
        Assert.notNull(SourceDataSegment, "政策数据源行程信息为空");
        return this.updateById(SourceDataSegment);
    }

    @Override
    public SourceDataSegment getSourceDataSegmentById(String id){
        return  this.getById(id);
    }
}
