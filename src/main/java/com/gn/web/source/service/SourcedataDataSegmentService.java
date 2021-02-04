package com.gn.web.source.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.source.entity.SourcedataDataSegment;

import java.util.List;

/**
 * <p>
 * 政策数据源行程信息 服务类
 * </p>
 *
 * @author gwk
 * @since 2021-02-03
 */
public interface SourcedataDataSegmentService extends IService<SourcedataDataSegment> {

    /**
     * 查询政策数据源行程信息分页数据
     *
     * @param page      分页参数
     * @param sourcedataDataSegment 查询条件
     * @return IPage<SourcedataDataSegment>
     */
     IPage<SourcedataDataSegment> pageSourcedataDataSegment(Page<SourcedataDataSegment> page, SourcedataDataSegment sourcedataDataSegment);

    /**
     * 新增政策数据源行程信息
     *
     * @param sourcedataDataSegment 政策数据源行程信息
     * @return boolean
     */
    boolean saveSourcedataDataSegment(SourcedataDataSegment sourcedataDataSegment);

    /**
     * 删除政策数据源行程信息
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeSourcedataDataSegment(String id);

    /**
     * 批量删除政策数据源行程信息
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeSourcedataDataSegmentByIds(List<String> ids);

    /**
     * 修改政策数据源行程信息
     *
     * @param sourcedataDataSegment 政策数据源行程信息
     * @return boolean
     */
    boolean updateSourcedataDataSegment(SourcedataDataSegment sourcedataDataSegment);

    /**
     * id查询数据
     *
     * @param id id
     * @return SourcedataDataSegment
     */
    SourcedataDataSegment getSourcedataDataSegmentById(String id);
}
