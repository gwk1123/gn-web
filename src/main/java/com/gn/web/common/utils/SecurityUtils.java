package com.gn.web.common.utils;

import com.gn.web.common.exception.CustomException;
import com.gn.web.sys.auth.bo.AdminUserDetails;
import com.gn.web.sys.entity.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 *
 * @author ruoyi
 */
public class SecurityUtils
{

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 获取用户账户
     **/
    public static String getUsername()
    {
        try
        {
            SysUser sysUser=getLoginUser();
            return  sysUser.getUserName();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户账户异常", UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static SysUser getLoginUser()
    {
        try
        {
            AdminUserDetails adminUserDetails =  (AdminUserDetails) getAuthentication().getPrincipal();
            return adminUserDetails.getUmsAdmin();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户信息异常", UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}
