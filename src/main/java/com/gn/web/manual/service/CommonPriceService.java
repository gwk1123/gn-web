package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.CommonPrice;

import java.util.List;

/**
 * <p>
 * 全平台调价 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
public interface CommonPriceService extends IService<CommonPrice> {

    /**
     * 查询全平台调价分页数据
     *
     * @param page      分页参数
     * @param commonPrice 查询条件
     * @return IPage<CommonPrice>
     */
     IPage<CommonPrice> pageCommonPrice(Page<CommonPrice> page, CommonPrice commonPrice);

    /**
     * 新增全平台调价
     *
     * @param commonPrice 全平台调价
     * @return boolean
     */
    boolean saveCommonPrice(CommonPrice commonPrice);

    /**
     * 删除全平台调价
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeCommonPrice(String id);

    /**
     * 批量删除全平台调价
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeCommonPriceByIds(List<String> ids);

    /**
     * 修改全平台调价
     *
     * @param commonPrice 全平台调价
     * @return boolean
     */
    boolean updateCommonPrice(CommonPrice commonPrice);

    /**
     * id查询数据
     *
     * @param id id
     * @return CommonPrice
     */
    CommonPrice getCommonPriceById(String id);
}
