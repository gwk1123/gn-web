package com.gn.web.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author liuyc
 * @since 2020-08-01
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询用户信息表分页数据
     *
     * @param page      分页参数
     * @param sysUser 查询条件
     * @return IPage<SysUser>
     */
     IPage<SysUser> pageSysUser(Page<SysUser> page,SysUser sysUser);

    /**
     * 新增用户信息表
     *
     * @param sysUser 用户信息表
     * @return boolean
     */
    void saveSysUser(SysUser sysUser);

    /**
     * 删除用户信息表
     *
     * @param userId 主键
     * @return boolean
     */
    boolean removeSysUser(String userId);


    /**
     * 修改用户信息表
     *
     * @param sysUser 用户信息表
     * @return boolean
     */
    boolean updateSysUser(SysUser sysUser);

    /**
     * id查询数据
     *
     * @param id id
     * @return SysUser
     */
    SysUser getSysUserById(String id);

    /**
     * 跟据用户账号查询
     * @param username
     * @return
     */
    SysUser getAdminByUsername(String username);

    /**
     * 跟据用户id查询角色集合
     * @param sysUserId
     * @return
     */
    List<SysRole> getRoleList(Long sysUserId);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 校验登录账号是否存在
     * @param userName
     * @return
     */
    String checkUserNameUnique(String userName);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SysUser user);
}
