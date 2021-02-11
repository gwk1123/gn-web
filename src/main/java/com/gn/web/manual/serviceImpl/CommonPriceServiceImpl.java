package com.gn.web.manual.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.constant.DirectConstants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.manual.entity.CommonPrice;
import com.gn.web.manual.entity.OtaRule;
import com.gn.web.manual.mapper.CommonPriceMapper;
import com.gn.web.manual.service.CommonPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 全平台调价 服务实现类
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
@Service
public class CommonPriceServiceImpl extends ServiceImpl<CommonPriceMapper, CommonPrice> implements CommonPriceService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public IPage<CommonPrice> pageCommonPrice(Page<CommonPrice> page, CommonPrice commonPrice){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<CommonPrice> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCommonPrice(CommonPrice commonPrice){
        Assert.notNull(commonPrice, "全平台调价为空");
        return this.save(commonPrice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeCommonPrice(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeCommonPriceByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCommonPrice(CommonPrice commonPrice){
        Assert.notNull(commonPrice, "全平台调价为空");
        return this.updateById(commonPrice);
    }

    @Override
    public CommonPrice getCommonPriceById(String id){
        return  this.getById(id);
    }

    public void saveOrUpdateCache(CommonPrice commonPrice){
        if(DirectConstants.NORMAL.equals(commonPrice.getStatus())){
            redisCache.addHashMap(DirectConstants.COMMON_PRICE+"_"+commonPrice.getSourceType(),String.valueOf(commonPrice.getId()),commonPrice);
        }else {
            redisCache.removeHashKey(DirectConstants.COMMON_PRICE+"_"+commonPrice.getSourceType(),String.valueOf(commonPrice.getId()));
        }
    }

}
