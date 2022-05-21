package com.nsolm.blog.admin.service;

import com.nsolm.blog.admin.pojo.Admin;
import com.nsolm.blog.admin.pojo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/14 14:32
 * @Description :
 */

@Service
@Slf4j
public class AuthService {

    @Resource
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        String requestURI = request.getRequestURI();

        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }

        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null){
            return false;
        }
        // 认为是超级管理员
        if( 1== admin.getId()){
            return true;
        }
        Long id = admin.getId();
        List<Permission> permissionList = adminService.findPermissionById(id);
        requestURI = StringUtils.split(requestURI, '?')[0];
        log.info("===========requestURI = {} ==========",requestURI);
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }


}
