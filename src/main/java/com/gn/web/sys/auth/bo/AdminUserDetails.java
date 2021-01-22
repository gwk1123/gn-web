package com.gn.web.sys.auth.bo;

import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * Created by macro on 2018/4/26.
 */
public class AdminUserDetails implements UserDetails {
    private SysUser umsAdmin;
    private List<SysRole> roleList;
    public AdminUserDetails(SysUser umsAdmin, List<SysRole> roleList) {
        this.umsAdmin = umsAdmin;
        this.roleList = roleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        if(CollectionUtils.isEmpty(roleList)){
            return null;
        }
        return roleList.stream().filter(Objects::nonNull)
                .map(role ->new SimpleGrantedAuthority(role.getRoleKey()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }

    public SysUser getUmsAdmin() {
        return umsAdmin;
    }

    public void setUmsAdmin(SysUser umsAdmin) {
        this.umsAdmin = umsAdmin;
    }
}
