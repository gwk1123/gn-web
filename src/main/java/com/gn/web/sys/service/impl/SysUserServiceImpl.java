package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gn.web.common.exception.CustomException;
import com.gn.web.common.utils.SecurityUtils;
import com.gn.web.sys.auth.bo.AdminUserDetails;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.entity.SysUserRole;
import com.gn.web.sys.mapper.SysUserMapper;
import com.gn.web.sys.service.SysRoleService;
import com.gn.web.sys.service.SysUserRoleService;
import com.gn.web.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    public SysUserServiceImpl(SysRoleService sysRoleService, SysUserRoleService sysUserRoleService) {
        this.sysRoleService = sysRoleService;
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    public  IPage<SysUser> pageSysUser(Page<SysUser> page, SysUser sysUser){
        page = Optional.ofNullable(page).orElse(new Page<>());
        QueryWrapper<SysUser> queryWrapperQueryWrapper = buildQueryWrapper( sysUser);
        return  this.page(page, queryWrapperQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUser(SysUser sysUser){
        Assert.notNull(sysUser, "用户信息表为空");
        // 新增用户信息
        this.save(sysUser);
        // 新增用户与角色管理
        sysUserRoleService.saveUserRole(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeSysUser(String userId){
        Assert.hasText(userId, "主键为空");
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper=new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        sysUserRoleService.remove(sysUserRoleQueryWrapper);
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        sysUserQueryWrapper.lambda().eq(SysUser::getUserId,userId);
        return this.remove(sysUserQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSysUser(SysUser sysUser){
        Assert.notNull(sysUser, "用户信息表为空");

        Long userId = sysUser.getUserId();
        // 删除用户与角色关联
        sysUserRoleService.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        sysUserRoleService.saveUserRole(sysUser);
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        sysUserQueryWrapper.lambda().eq(SysUser::getUserId,userId);
        return this.update(sysUser,sysUserQueryWrapper);
    }

    @Override
    public SysUser getSysUserById(String id){
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper();
        queryWrapper.lambda().eq(SysUser::getUserId,id);
        return  this.getOne(queryWrapper);
    }

    @Override
    public SysUser getAdminByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUserName, username);
        SysUser umsAdmin = this.getOne(queryWrapper);
        return umsAdmin;
    }

    @Override
    public List<SysRole> getRoleList(Long sysUserId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId, sysUserId);
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(queryWrapper);
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return null;
        }
        List<Long> roleIds = sysUserRoles.stream().filter(Objects::nonNull).map(SysUserRole::getRoleId).collect(Collectors.toList());
        QueryWrapper<SysRole> roleQueryWrapper=new QueryWrapper<>();
        roleQueryWrapper.lambda().in(SysRole::getRoleId,roleIds);
        List<SysRole> roles = sysRoleService.list(roleQueryWrapper);
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        SysUser sysUser = getAdminByUsername(username);
        if (sysUser != null) {
            List<SysRole> roleList = getRoleList(sysUser.getUserId());
            return new AdminUserDetails(sysUser, roleList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 构建查询条件
     * @param sysUser
     * @return
     */
    public QueryWrapper<SysUser> buildQueryWrapper(SysUser sysUser){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(sysUser != null){

            queryWrapper.lambda().like(StringUtils.isNotBlank(sysUser.getUserName()),SysUser::getUserName,sysUser.getUserName())
                    .like(StringUtils.isNotBlank(sysUser.getNickName()),SysUser::getNickName,sysUser.getNickName())
                    .eq(StringUtils.isNotBlank(sysUser.getPhonenumber()),SysUser::getPhonenumber,sysUser.getPhonenumber())
                    .orderByAsc(SysUser::getCreateTime);
        }
        SysUser sysUserIsAdmin = SecurityUtils.getLoginUser();
        if(sysUserIsAdmin.isAdmin()){
            return queryWrapper;
        }
        queryWrapper.lambda().eq(SysUser::getCreateBy, sysUserIsAdmin.getUserName()).or()
                .eq(SysUser::getUserName,SecurityUtils.getUsername());
        return queryWrapper;
    }


    /**
     * 校验登录账号是否存在
     * @param userName
     * @return
     */
    @Override
    public String checkUserNameUnique(String userName){
        QueryWrapper<SysUser> sysUserQueryWrapper=new QueryWrapper<>();
        sysUserQueryWrapper.lambda().eq(SysUser::getUserName,userName);
        SysUser sysUser = this.getOne(sysUserQueryWrapper);
        return sysUser==null?"0":"1";
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (user.getUserId()!= null && user.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }


}
