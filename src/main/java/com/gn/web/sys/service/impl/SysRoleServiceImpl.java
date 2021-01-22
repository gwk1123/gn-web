package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.exception.CustomException;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.common.utils.StringUtils;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysRoleMenu;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.entity.SysUserRole;
import com.gn.web.sys.mapper.SysRoleMapper;
import com.gn.web.sys.service.SysRoleMenuService;
import com.gn.web.sys.service.SysRoleService;
import com.gn.web.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysUserRoleService sysUserRoleService;

    private final SysRoleMenuService sysRoleMenuService;


    public SysRoleServiceImpl(SysUserRoleService sysUserRoleService, SysRoleMenuService sysRoleMenuService) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    public  IPage<SysRole> pageSysRole(Page<SysRole> page, SysRole sysRole){

        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper queryWrapper= buildQueryWrapper( sysRole);
        return  this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveSysRole(SysRole sysRole){
        Assert.notNull(sysRole, "角色信息表为空");
        this.save(sysRole);
        return saveRoleMenu(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysRole(String roleId){
        Assert.hasText(roleId, "主键为空");
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper=new QueryWrapper<>();
        sysRoleMenuQueryWrapper.lambda().eq(SysRoleMenu::getRoleId,roleId);
        sysRoleMenuService.remove(sysRoleMenuQueryWrapper);
        QueryWrapper<SysRole> sysRoleQueryWrapper=new QueryWrapper<>();
        sysRoleQueryWrapper.lambda().eq(SysRole::getRoleId,roleId);
        return this.remove(sysRoleQueryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSysRole(SysRole sysRole){
        Assert.notNull(sysRole, "角色信息表为空");

        // 修改角色信息
        QueryWrapper<SysRole> sysRoleQueryWrapper=new QueryWrapper<>();
        sysRoleQueryWrapper.lambda().eq(SysRole::getRoleId,sysRole.getRoleId());
        this.update(sysRole,sysRoleQueryWrapper);
        // 删除角色与菜单关联
        this.removeRoleMenuByRoleId(sysRole);
        return this.saveRoleMenu(sysRole);
    }

    @Override
    public SysRole getSysRoleByRoleId(String roleId){
        QueryWrapper<SysRole> sysRoleQueryWrapper=new QueryWrapper<>();
        sysRoleQueryWrapper.lambda().eq(SysRole::getRoleId,roleId);
        return this.getOne(sysRoleQueryWrapper);
    }


    @Override
    public Set<String> getRoleByUserId(Long userId){
        QueryWrapper<SysUserRole> userRoleQueryWrapper=new QueryWrapper<>();
        userRoleQueryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        List<SysUserRole> userRoles= sysUserRoleService.list(userRoleQueryWrapper);
        if(CollectionUtils.isEmpty(userRoles)){
            return null;
        }
        List<Long> roleIds=userRoles.stream().filter(Objects::nonNull).map(SysUserRole::getRoleId).collect(Collectors.toList());
        QueryWrapper<SysRole> roleQueryWrapper=new QueryWrapper<>();
        roleQueryWrapper.lambda().in(SysRole::getRoleId,roleIds);
        List<SysRole> roles = this.list(roleQueryWrapper);
        if(CollectionUtils.isEmpty(roles)){
            return null;
        }
        Set<String> permsSet = new HashSet<>();
        roles.stream().filter(Objects::nonNull).forEach(perm ->{
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        });
        return permsSet;
    }

    @Override
    public List<SysRole> selectRoleAll(){
       return this.list();
    }

    @Override
    public List<Long> selectRoleListByUserId(Long userId){
        if(userId == null){
            return null;
        }
        QueryWrapper<SysUserRole> userRoleQueryWrapper=new QueryWrapper<>();
        userRoleQueryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        List<SysUserRole> userRoles= sysUserRoleService.list(userRoleQueryWrapper);
        if(CollectionUtils.isEmpty(userRoles)){
            return null;
        }
        return userRoles.stream().filter(Objects::nonNull).map(SysUserRole::getRoleId).collect(Collectors.toList());

    }

    public QueryWrapper buildQueryWrapper(SysRole sysRole){
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper();
        if(sysRole != null){
            queryWrapper.lambda().like(StringUtils.isNotBlank(sysRole.getRoleName()),SysRole::getRoleName,sysRole.getRoleName()).
                    eq(StringUtils.isNotBlank(sysRole.getRoleKey()),SysRole::getRoleKey,sysRole.getRoleKey()).
            eq(StringUtils.isNotBlank(sysRole.getStatus()),SysRole::getStatus,sysRole.getStatus())
            .orderByAsc(SysRole::getCreateTime);
        }
        SysUser sysUser = SecurityUtils.getLoginUser();
        if(sysUser.isAdmin()){
            return queryWrapper;
        }
        QueryWrapper<SysUserRole>sysUserRoleQueryWrapper=new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getUserId,sysUser.getUserId());
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(sysUserRoleQueryWrapper);
        if(!CollectionUtils.isEmpty(sysUserRoles)){
            List<Long> roleIds = sysUserRoles.stream().filter(Objects::nonNull).map(SysUserRole::getRoleId).collect(Collectors.toList());
            queryWrapper.and(wrapper ->wrapper.lambda().eq(SysRole::getCreateBy, SecurityUtils.getUsername()).or()
                    .in(SysRole::getRoleId,roleIds));
         }else {
            queryWrapper.lambda().eq(SysRole::getCreateBy, SecurityUtils.getUsername());
        }
        return queryWrapper;
    }


    @Override
    public String checkRoleNameOrRoleKeyUnique(SysRole sysRole, String type){
        QueryWrapper<SysRole> sysRoleQueryWrapper=new QueryWrapper<>();
        sysRoleQueryWrapper.lambda().eq(StringUtils.isNotBlank(sysRole.getRoleName()),SysRole::getRoleName,sysRole.getRoleName()).or()
                .eq(StringUtils.isNotBlank(sysRole.getRoleKey()),SysRole::getRoleKey,sysRole.getRoleKey());
        List<SysRole> sysRoles = this.list(sysRoleQueryWrapper);
        if("save".equals(type)) {
            if (sysRoles != null && sysRoles.size() > 0) {
                return "1";
            }
        }else {
            if (sysRoles != null && sysRoles.size() > 1) {
                return "1";
            }
        }
        return "0";
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员角色");
        }
    }


    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int saveRoleMenu(SysRole role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        if(CollectionUtils.isEmpty(role.getMenuIds())){
            return rows;
        }
        role.getMenuIds().stream().filter(Objects::nonNull).forEach( e ->{
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(e);
            list.add(rm);
        });
        sysRoleMenuService.saveBatch(list);
        return rows;
    }

    public void removeRoleMenuByRoleId(SysRole sysRole){
        QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper=new QueryWrapper<>();
        sysRoleMenuQueryWrapper.lambda().eq(SysRoleMenu::getRoleId,sysRole.getRoleId());
        sysRoleMenuService.remove(sysRoleMenuQueryWrapper);
    }

}
