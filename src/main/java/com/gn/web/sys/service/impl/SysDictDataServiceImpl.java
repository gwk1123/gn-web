package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.UserConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.sys.entity.SysDictData;
import com.gn.web.sys.mapper.SysDictDataMapper;
import com.gn.web.sys.service.SysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    private final RedisCache redisCache;

    public SysDictDataServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSysDictData(SysDictData sysDictData){
        Assert.notNull(sysDictData, "字典数据表为空");
        return this.save(sysDictData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysDictData(String dictCode){
        Assert.hasText(dictCode, "主键为空");
        QueryWrapper<SysDictData> sysDictDataQueryWrapper=new QueryWrapper<>();
        sysDictDataQueryWrapper.lambda().eq(SysDictData::getDictCode,dictCode);
        return this.remove(sysDictDataQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysDictData(SysDictData sysDictData){
        Assert.notNull(sysDictData, "字典数据表为空");
        QueryWrapper<SysDictData> sysDictDataQueryWrapper=new QueryWrapper<>();
        sysDictDataQueryWrapper.lambda().eq(SysDictData::getDictCode,sysDictData.getDictCode());
        return this.update(sysDictData,sysDictDataQueryWrapper);
    }

    @Override
    public SysDictData getSysDictDataById(String id){
        return  this.getById(id);
    }



    public IPage<SysDictData> listDictDataList(Page<SysDictData> page,SysDictData sysDictData){
        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SysDictData> queryWrapper = buildQueryWrapper( sysDictData);
        return this.page(page,queryWrapper);
    }

    @Override
    public List<SysDictData> getDictType(String dictType){
        QueryWrapper<SysDictData> sysDictDataQueryWrapper=new QueryWrapper<>();
        sysDictDataQueryWrapper.lambda().eq(SysDictData::getDictType,dictType);
        List<SysDictData> sysDictDatas = this.list(sysDictDataQueryWrapper);
        return sysDictDatas;
    }

    @Override
    public SysDictData getDictTypeBySysDictData(String dictType){
        QueryWrapper<SysDictData> sysDictDataQueryWrapper=new QueryWrapper<>();
        sysDictDataQueryWrapper.lambda().eq(SysDictData::getDictType,dictType)
        .eq(SysDictData::getStatus, UserConstants.NORMAL);
        return this.getOne(sysDictDataQueryWrapper);
    }


    public QueryWrapper<SysDictData> buildQueryWrapper(SysDictData sysDictData){
        QueryWrapper<SysDictData> sysDictDataQueryWrapper=new QueryWrapper<>();
        if(sysDictData != null){
            sysDictDataQueryWrapper.lambda().like(StringUtils.isNotBlank(sysDictData.getDictLabel()),
                    SysDictData::getDictLabel,sysDictData.getDictLabel())
                    .eq(StringUtils.isNotBlank(sysDictData.getDictType()),SysDictData::getDictType,sysDictData.getDictType())
            .eq(StringUtils.isNotBlank(sysDictData.getStatus()),SysDictData::getStatus,sysDictData.getStatus());
        }
        sysDictDataQueryWrapper.lambda().orderByAsc(SysDictData::getCreateTime);
        return sysDictDataQueryWrapper;
    }

}
