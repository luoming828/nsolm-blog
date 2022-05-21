package com.nsolm.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsolm.blog.admin.mapper.AdminMapper;
import com.nsolm.blog.admin.pojo.Admin;
import com.nsolm.blog.admin.pojo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/14 14:24
 * @Description :
 */

@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    public List<Permission> findPermissionById(Long adminId) {
        return adminMapper.findPermissionByAdminId(adminId);
    }
}
