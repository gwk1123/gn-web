package com.gn.web.source.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.source.entity.SourcedataDataSegment;
import com.gn.web.source.mapper.SourcedataDataSegmentMapper;
import com.gn.web.source.service.SourcedataDataSegmentService;
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
public class SourcedataDataSegmentServiceImpl extends ServiceImpl<SourcedataDataSegmentMapper, SourcedataDataSegment> implements SourcedataDataSegmentService {

    @Override
    public IPage<SourcedataDataSegment> pageSourcedataDataSegment(Page<SourcedataDataSegment> page, SourcedataDataSegment sourcedataDataSegment){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SourcedataDataSegment> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSourcedataDataSegment(SourcedataDataSegment sourcedataDataSegment){
        Assert.notNull(sourcedataDataSegment, "政策数据源行程信息为空");
        return this.save(sourcedataDataSegment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourcedataDataSegment(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSourcedataDataSegmentByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSourcedataDataSegment(SourcedataDataSegment sourcedataDataSegment){
        Assert.notNull(sourcedataDataSegment, "政策数据源行程信息为空");
        return this.updateById(sourcedataDataSegment);
    }

    @Override
    public SourcedataDataSegment getSourcedataDataSegmentById(String id){
        return  this.getById(id);
    }
}
