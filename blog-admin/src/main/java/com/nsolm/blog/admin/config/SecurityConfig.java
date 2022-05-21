package com.nsolm.blog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/14 9:43
 * @Description :
 */

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        String nsolm = new BCryptPasswordEncoder().encode("1234");
        System.out.println(nsolm);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 无需认证
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/plugins/**").permitAll()
                // 需要权限认证
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)")
                // 登录成功即可
                .antMatchers("/pages/**").authenticated()
                .and().formLogin()
                .loginPage("/login.html") //自定义登录页面
                .loginProcessingUrl("/login") //登录处理接口
                .usernameParameter("username") //定义登录时的用户名的key 默认为username
                .passwordParameter("password") //定义登陆时的密码key 默认为password
                .defaultSuccessUrl("/pages/main.html")
                .failureUrl("/login.html")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable() // csrf关闭 如果自定义登录 需要关闭
                .headers().frameOptions().sameOrigin();
    }

}
