package com.gn.web.source.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.source.entity.SourcedataData;

import java.util.List;

/**
 * <p>
 * 政策数据源 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
public interface SourcedataDataService extends IService<SourcedataData> {

    /**
     * 查询政策数据源分页数据
     *
     * @param page      分页参数
     * @param sourcedataData 查询条件
     * @return IPage<SourcedataData>
     */
     IPage<SourcedataData> pageSourcedataData(Page<SourcedataData> page, SourcedataData sourcedataData);

    /**
     * 新增政策数据源
     *
     * @param sourcedataData 政策数据源
     * @return boolean
     */
    boolean saveSourcedataData(SourcedataData sourcedataData);

    /**
     * 删除政策数据源
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSourcedataData(String id);

    /**
     * 批量删除政策数据源
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSourcedataDataByIds(List<String> ids);

    /**
     * 修改政策数据源
     *
     * @param sourcedataData 政策数据源
     * @return boolean
     */
    boolean updateSourcedataData(SourcedataData sourcedataData);

    /**
     * id查询数据
     *
     * @param id id
     * @return SourcedataData
     */
    SourcedataData getSourcedataDataById(String id);
}
