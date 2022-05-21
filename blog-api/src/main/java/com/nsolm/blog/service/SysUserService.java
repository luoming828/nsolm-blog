package com.nsolm.blog.service;

import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.UserVo;

public interface SysUserService {


    UserVo findUserVoById(Long authorId);

    SysUser findUserById(Long authorId);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/5 14:47
     *@Description : 根据用户名查询
     */
    SysUser findUserByAccount(String account);


    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/5 14:47
     *@Description : 保存用户
     */
    void save(SysUser sysUser);
}
