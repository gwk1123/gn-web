package com.gn.web.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.sys.entity.SysDictData;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
public interface SysDictDataService extends IService<SysDictData> {


    /**
     * 新增字典数据表
     *
     * @param sysDictData 字典数据表
     * @return boolean
     */
    boolean saveSysDictData(SysDictData sysDictData);

    /**
     * 删除字典数据表
     *
     * @param dictCode 主键
     * @return boolean
     */
    boolean removeSysDictData(String dictCode);


    /**
     * 修改字典数据表
     *
     * @param sysDictData 字典数据表
     * @return boolean
     */
    boolean updateSysDictData(SysDictData sysDictData);

    /**
     * id查询数据
     *
     * @param id id
     * @return SysDictData
     */
    SysDictData getSysDictDataById(String id);


    /**
     * 查询字典数据表分页数据
     * @param page
     * @param sysDictData
     * @return
     */
    IPage<SysDictData> listDictDataList(Page<SysDictData> page,SysDictData sysDictData);


    /**
     * dictType查询数据
     * @param dictType
     * @return
     */
    List<SysDictData> getDictType( String dictType);

    /**
     * 根据字典类型查询字典数据信息(单个对象)
     * @param dictType
     * @return
     */
    SysDictData getDictTypeBySysDictData( String dictType);
}
