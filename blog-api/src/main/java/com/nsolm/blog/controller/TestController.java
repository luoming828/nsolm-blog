package com.nsolm.blog.controller;

import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.utils.UserThreadLocal;
import com.nsolm.blog.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/5 15:34
 * @Description :
 */

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        return Result.success(123);
    }

}
