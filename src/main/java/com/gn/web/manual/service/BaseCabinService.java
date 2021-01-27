package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.BaseCabin;

import java.util.List;

/**
 * <p>
 * 政策基础舱位 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-01-27
 */
public interface BaseCabinService extends IService<BaseCabin> {

    /**
     * 查询政策基础舱位分页数据
     *
     * @param page      分页参数
     * @param baseCabin 查询条件
     * @return IPage<BaseCabin>
     */
     IPage<BaseCabin> pageBaseCabin(Page<BaseCabin> page, BaseCabin baseCabin);

    /**
     * 新增政策基础舱位
     *
     * @param baseCabin 政策基础舱位
     * @return boolean
     */
    boolean saveBaseCabin(BaseCabin baseCabin);

    /**
     * 删除政策基础舱位
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeBaseCabin(String id);

    /**
     * 批量删除政策基础舱位
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeBaseCabinByIds(List<String> ids);

    /**
     * 修改政策基础舱位
     *
     * @param baseCabin 政策基础舱位
     * @return boolean
     */
    boolean updateBaseCabin(BaseCabin baseCabin);

    /**
     * id查询数据
     *
     * @param id id
     * @return BaseCabin
     */
    BaseCabin getBaseCabinById(String id);
}
