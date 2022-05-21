package com.nsolm.blog.admin.controller;

import com.nsolm.blog.admin.model.params.PageParam;
import com.nsolm.blog.admin.pojo.Permission;
import com.nsolm.blog.admin.service.PermissionService;
import com.nsolm.blog.admin.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/13 15:00
 * @Description :
 */

@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return  permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result addPermission(@RequestBody Permission permission){
        return  permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result updatePermission(@RequestBody Permission permission){
        return  permissionService.update(permission);
    }

    @GetMapping("permission/delete")
    public Result deletePermission(@RequestParam("id") Long id){
        return  permissionService.delete(id);
    }

}
