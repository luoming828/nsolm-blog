package com.nsolm.blog.service;

import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.LoginParams;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {
    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/2 10:49
     *@Description : 登录功能
     */
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams loginParams);
}
