package com.gn.web.security.component;

import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gn.web.sys.entity.SysRole;
import com.gn.web.sys.entity.SysRoleMenu;
import com.gn.web.sys.service.SysRoleMenuService;
import com.gn.web.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态权限数据源，用于获取动态权限规则
 * Created by macro on 2020/2/7.
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleService sysRoleService;


    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (CollectionUtils.isEmpty(configAttributeMap)) {
            this.loadDataSource();
        }
        List<ConfigAttribute>  configAttributes = new ArrayList<>();
        //configAttributes 返回null是不会校验权限，所以初始化一个ROLE的无用role_init
//        configAttributes.add(new org.springframework.security.access.SecurityConfig("role_init"));
        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();
        String path = URLUtil.getPath(url);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) {
                QueryWrapper<SysRoleMenu> sysRoleMenuQueryWrapper=new QueryWrapper<>();
                sysRoleMenuQueryWrapper.lambda().eq(SysRoleMenu::getMenuId,Long.valueOf(configAttributeMap.get(pattern).toString()));
                List<SysRoleMenu> sysRoleMenus =sysRoleMenuService.list(sysRoleMenuQueryWrapper);
                if(!CollectionUtils.isEmpty(sysRoleMenus)){
                    List<Long> roleIds = sysRoleMenus.stream().filter(Objects::nonNull)
                            .map(SysRoleMenu::getRoleId).collect(Collectors.toList());
                    QueryWrapper<SysRole> roleQueryWrapper=new QueryWrapper<>();
                    roleQueryWrapper.lambda().in(SysRole::getRoleId,roleIds);
                    List<SysRole> roles= sysRoleService.list(roleQueryWrapper);
                    roles.stream().filter(Objects::nonNull).forEach(e ->{
                        configAttributes.add(new org.springframework.security.access.SecurityConfig(e.getRoleKey()));
                    });
                }
            }
        }
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
