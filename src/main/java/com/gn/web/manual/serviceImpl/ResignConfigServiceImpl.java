package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.manual.entity.ResignConfig;
import com.gn.web.manual.mapper.ResignConfigMapper;
import com.gn.web.manual.service.ResignConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gwk
 * @since 2020-11-17
 */
@Service
public class ResignConfigServiceImpl extends ServiceImpl<ResignConfigMapper, ResignConfig> implements ResignConfigService {

    @Override
    public  IPage<ResignConfig> pageResignConfig(Page<ResignConfig> page, ResignConfig resignConfig){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<ResignConfig> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveResignConfig(ResignConfig resignConfig){
        Assert.notNull(resignConfig, "为空");
        return this.save(resignConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeResignConfig(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeResignConfigByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateResignConfig(ResignConfig resignConfig){
        Assert.notNull(resignConfig, "为空");
        return this.updateById(resignConfig);
    }

    @Override
    public ResignConfig getResignConfigById(String id){
        return  this.getById(id);
    }
}
