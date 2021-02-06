package com.gn.web.source.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.source.entity.SourceDataSegment;

import java.util.List;

/**
 * <p>
 * 政策数据源行程信息 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
public interface SourceDataSegmentService extends IService<SourceDataSegment> {

    /**
     * 查询政策数据源行程信息分页数据
     *
     * @param page      分页参数
     * @param SourceDataSegment 查询条件
     * @return IPage<SourceDataSegment>
     */
     IPage<SourceDataSegment> pageSourceDataSegment(Page<SourceDataSegment> page, SourceDataSegment SourceDataSegment);

    /**
     * 新增政策数据源行程信息
     *
     * @param SourceDataSegment 政策数据源行程信息
     * @return boolean
     */
    boolean saveSourceDataSegment(SourceDataSegment SourceDataSegment);

    /**
     * 删除政策数据源行程信息
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSourceDataSegment(String id);

    /**
     * 批量删除政策数据源行程信息
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSourceDataSegmentByIds(List<String> ids);

    /**
     * 修改政策数据源行程信息
     *
     * @param SourceDataSegment 政策数据源行程信息
     * @return boolean
     */
    boolean updateSourceDataSegment(SourceDataSegment SourceDataSegment);

    /**
     * id查询数据
     *
     * @param id id
     * @return SourceDataSegment
     */
    SourceDataSegment getSourceDataSegmentById(String id);
}
