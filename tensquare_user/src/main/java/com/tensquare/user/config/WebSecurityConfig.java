package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //anthoriziRequests所有security全注解配置实现的开端，表示说明需要的权限‘
        //需要的权限分为两部分，第一部分是拦截的路径，第二部分访问该路径所需要的权限
        //anyMachers表示拦截什么路径，permitAll任何权限都能访问，直接放行所有
        //anyRequest任何的请求，authenticated认证之后才能访问
        //.and().csrf().disable()固定写法，表示是csrf（网络攻击技术）拦截失效
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
