package com.gn.web.source.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gn.web.source.entity.SourceData;

import java.util.List;

/**
 * <p>
 * 政策数据源 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-02-05
 */
public interface SourceDataService extends IService<SourceData> {

    /**
     * 查询政策数据源分页数据
     *
     * @param page      分页参数
     * @param sourceData 查询条件
     * @return IPage<SourceData>
     */
     IPage<SourceData> pageSourceData(Page<SourceData> page,SourceData sourceData);

    /**
     * 新增政策数据源
     *
     * @param sourceData 政策数据源
     * @return boolean
     */
    boolean saveSourceData(SourceData sourceData);

    /**
     * 删除政策数据源
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSourceData(String id);

    /**
     * 批量删除政策数据源
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSourceDataByIds(List<String> ids);

    /**
     * 修改政策数据源
     *
     * @param sourceData 政策数据源
     * @return boolean
     */
    boolean updateSourceData(SourceData sourceData);

    /**
     * id查询数据
     *
     * @param id id
     * @return SourceData
     */
    SourceData getSourceDataById(String id);
}
