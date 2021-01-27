package com.gn.web.manual.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.manual.entity.ResignConfig;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gwk
 * @since 2020-11-17
 */
public interface ResignConfigService extends IService<ResignConfig> {

    /**
     * 查询分页数据
     *
     * @param page      分页参数
     * @param resignConfig 查询条件
     * @return IPage<ResignConfig>
     */
     IPage<ResignConfig> pageResignConfig(Page<ResignConfig> page,ResignConfig resignConfig);

    /**
     * 新增
     *
     * @param resignConfig
     * @return boolean
     */
    boolean saveResignConfig(ResignConfig resignConfig);

    /**
     * 删除
     *
     * @param id 主键
     * @return boolean
     */
    boolean removeResignConfig(String id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return boolean
     */
    boolean removeResignConfigByIds(List<String> ids);

    /**
     * 修改
     *
     * @param resignConfig
     * @return boolean
     */
    boolean updateResignConfig(ResignConfig resignConfig);

    /**
     * id查询数据
     *
     * @param id id
     * @return ResignConfig
     */
    ResignConfig getResignConfigById(String id);
}
