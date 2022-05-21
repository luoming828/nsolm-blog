package com.nsolm.blog.controller;

import com.nsolm.blog.service.LoginService;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.LoginParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 10:39
 * @Description :
 */


@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Resource
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){

        return loginService.logout(token);
    }
}
