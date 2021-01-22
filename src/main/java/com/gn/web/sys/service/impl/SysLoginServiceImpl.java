package com.gn.web.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gn.web.common.constant.Constants;
import com.gn.web.common.redis.RedisCache;
import com.gn.web.security.util.JwtTokenUtil;
import com.gn.web.sys.auth.bo.AdminUserDetails;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysUser;
import com.gn.web.sys.entity.SysUserRole;
import com.gn.web.sys.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginServiceImpl {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisCache redisCache;
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final SysRoleMenuService sysRoleMenuService;
    private final SysMenuService sysMenuService;

    public SysLoginServiceImpl(JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, RedisCache redisCache, SysUserService sysUserService, SysUserRoleService sysUserRoleService, SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService, SysMenuService sysMenuService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.redisCache = redisCache;
        this.sysUserService = sysUserService;
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
        this.sysMenuService = sysMenuService;
    }

    Logger logger = LoggerFactory.getLogger(SysLoginServiceImpl.class);

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = (String) redisCache.getString(verifyKey);
        redisCache.deleteKey(verifyKey);
        String token = null;
        try {
            if (captcha == null) {
                throw new BadCredentialsException("验证码为空");
            }
            if (!code.equalsIgnoreCase(captcha)) {
                throw new BadCredentialsException("验证码不匹配");
            }

            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录异常:{}" , e.getMessage());
        }
        return token;

    }


    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        SysUser sysUser = getSysUserByUsername(username);
        if (sysUser != null) {
            List<SysRole> roleList = getRoleList(sysUser.getUserId());
            return new AdminUserDetails(sysUser, roleList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    public SysUser getSysUserByUsername(String username) {
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(SysUser::getUserName, username);
        return sysUserService.getOne(userQueryWrapper);
    }

    public List<SysRole> getRoleList(Long userId) {
        QueryWrapper<SysUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.lambda().eq(SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = sysUserRoleService.list(userRoleQueryWrapper);
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }
        List<Long> roles = userRoles.stream().filter(Objects::nonNull).map(SysUserRole::getRoleId).collect(Collectors.toList());
        QueryWrapper<SysRole> roleQueryWrapper=new QueryWrapper<>();
        roleQueryWrapper.lambda().in(SysRole::getRoleId,roles);
        return sysRoleService.list(roleQueryWrapper);
    }


    public SysUser getLoginUser(HttpServletRequest request) {
        SysUser sysUser = null;
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.lambda().eq(SysUser::getUserName, username);
            sysUser = sysUserService.getOne(userQueryWrapper);
        }
        return sysUser;
    }


    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(sysRoleService.getRoleByUserId(user.getUserId()));
        }
        return roles;
    }


    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getPermissionBySysUser(SysUser user)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*/*/*");
        }
        else
        {
            perms.addAll(sysMenuService.getMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }


}
