package com.gn.web.direct.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.direct.entity.BaseCabin;
import com.gn.web.direct.mapper.BaseCabinMapper;
import com.gn.web.direct.service.BaseCabinService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 政策基础舱位 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Service
public class BaseCabinServiceImpl extends ServiceImpl<BaseCabinMapper, BaseCabin> implements BaseCabinService {

    @Override
    public IPage<BaseCabin> pageBaseCabin(Page<BaseCabin> page, BaseCabin baseCabin){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<BaseCabin> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBaseCabin(BaseCabin baseCabin){
        Assert.notNull(baseCabin, "政策基础舱位为空");
        return this.save(baseCabin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBaseCabin(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeBaseCabinByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBaseCabin(BaseCabin baseCabin){
        Assert.notNull(baseCabin, "政策基础舱位为空");
        return this.updateById(baseCabin);
    }

    @Override
    public BaseCabin getBaseCabinById(String id){
        return  this.getById(id);
    }
}
