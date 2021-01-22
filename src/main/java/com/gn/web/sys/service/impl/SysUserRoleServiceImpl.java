package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.entity.SysUserRole;
import com.gn.web.sys.mapper.SysUserRoleMapper;
import com.gn.web.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public  IPage<SysUserRole> pageSysUserRole(Page<SysUserRole> page, SysUserRole sysUserRole){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();

        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSysUserRole(SysUserRole sysUserRole){
        Assert.notNull(sysUserRole, "用户和角色关联表为空");
        return this.save(sysUserRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysUserRole(String id){
        Assert.hasText(id, "主键为空");
        return this.removeById(id);
    }


    @Override
    public SysUserRole getSysUserRoleById(String id){
        return  this.getById(id);
    }

    @Override
    public void saveUserRole(SysUser sysUser){
        List<Long> roleIds= sysUser.getRoleIds();
        if(CollectionUtils.isEmpty(roleIds)){
            return ;
        }
        List<SysUserRole> sysUserRoles=new ArrayList<>();
        roleIds.stream().filter(Objects::nonNull).forEach(e ->{
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(e);
            sysUserRoles.add(sysUserRole);
        });
        this.saveBatch(sysUserRoles);
    }

    @Override
    public void deleteUserRoleByUserId(Long userId){
        if(userId == null){
            return ;
        }
        QueryWrapper<SysUserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        this.remove(queryWrapper);
    }

}
