package com.gn.web.security.config;
//package com.mall.manage.security.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//public class SwaggerSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private static final String[] AUTH_WHITELIST = {
//
//            // -- swagger ui
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**"
//    };
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers("/**/*").denyAll();
//    }
//
//}
