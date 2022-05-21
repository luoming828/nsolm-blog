package com.nsolm.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.service.LoginService;
import com.nsolm.blog.utils.UserThreadLocal;
import com.nsolm.blog.vo.ErrorCode;
import com.nsolm.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: Luo Ming
 * @Date: 2022/5/5 15:11
 * @Description : 登录拦截器
 */


@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //在执行controller方法(Handler)之前执行
        /**
         * 1.需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
         * 2.判断token 是否为空 为空则未登录
         * 3.如果token不为空 则进行登录验证 loginService checkToken
         * 4.如果认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)){
            //handler 可能是资源RequestResourceHandler springboot程序 访问静态资源默认去classpath下的static目录去查询
            return true;
        }
        // 请求头中的token
        String token = request.getHeader("Authorization");
        log.info("================request start===================");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("================request end===================");
        // 判断token 是否为空 为空则未登录
        if (StringUtils.isBlank(token) || token.equals("undefined")){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        log.warn("==================={}================",token);
        // 如果token不为空 则进行登录验证 loginService checkToken
        SysUser sysUser = loginService.checkToken(token);
        if( sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "登录验证失败");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息,会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
