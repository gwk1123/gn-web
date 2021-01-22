package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.sys.entity.SysRoleMenu;
import com.gn.web.sys.mapper.SysRoleMenuMapper;
import com.gn.web.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public  IPage<SysRoleMenu> pageSysRoleMenu(Page<SysRoleMenu> page, SysRoleMenu sysRoleMenu){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSysRoleMenu(SysRoleMenu sysRoleMenu){
        Assert.notNull(sysRoleMenu, "角色和菜单关联表为空");
        return this.save(sysRoleMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysRoleMenu(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysRoleMenuByIds(List<String> ids){
        Assert.isTrue(!CollectionUtils.isEmpty(ids), "主键集合为空");
        return this.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysRoleMenu(SysRoleMenu sysRoleMenu){
        Assert.notNull(sysRoleMenu, "角色和菜单关联表为空");
        return this.updateById(sysRoleMenu);
    }

    @Override
    public SysRoleMenu getSysRoleMenuById(String id){
        return  this.getById(id);
    }
}
