package com.nsolm.blog.admin.service;

import com.nsolm.blog.admin.pojo.Admin;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/14 14:22
 * @Description :
 */

@Service
public class SecurityUserService implements UserDetailsService {

    @Resource
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 登录的时候会把username传递到这里
        // 通过username查询 admin 表 如果 admin 存在 将密码告诉 SpringSecurity
        // 如果不存在 返回null 认证失败
        Admin admin = adminService.findAdminByUsername(s);
        if (admin == null){
            return null;
        }
        UserDetails userDetails = new User(s,admin.getPassword(),new ArrayList<>());

        return userDetails;
    }
}
