package com.nsolm.blog.utils;

import com.nsolm.blog.dao.pojo.SysUser;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/5 16:01
 * @Description :
 */

public class UserThreadLocal {

    private UserThreadLocal(){

    }
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
