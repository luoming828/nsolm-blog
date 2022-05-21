package com.nsolm.blog.controller;

import com.nsolm.blog.service.LoginService;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.LoginParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/5 14:24
 * @Description :
 */

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams){
        return loginService.register(loginParams);


    }
}
