package com.nsolm.blog.controller;

import com.nsolm.blog.service.SysUserService;
import com.nsolm.blog.vo.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 15:35
 * @Description :
 */

@RestController
@RequestMapping("users")
public class UsersController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);

    }

}
