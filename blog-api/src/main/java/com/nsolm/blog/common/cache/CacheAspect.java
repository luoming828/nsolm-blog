package com.nsolm.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.nsolm.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/12 15:10
 * @Description :
 */

@Component
@Aspect
@Slf4j
public class CacheAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.nsolm.blog.common.cache.Cache)")
    public void pt(){}

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        try{
            Signature signature = pjp.getSignature();
            // 获得类名
            String className = pjp.getTarget().getClass().getSimpleName();
            // 调用的方法名
            String methodName = signature.getName();

            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
            String params = "";
            for (int i = 0; i < args.length ; i++) {
                if(args[i] != null){
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }
                else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)){
                // 加密 以防出现key过长及字符获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName,parameterTypes);
            // 获取cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            // 缓存过期时间
            long expire = annotation.expire();
            // 缓存名称
            String name = annotation.name();
            // 先从redis中获取
            String redisKey = name + "::" + className + "::" + params;

            String redisValue  = stringRedisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)){
                log.info("走了缓存~~~,{},{}",className,methodName);
                return JSON.parseObject(redisValue, Result.class);

            }
            Object proceed = pjp.proceed();
            stringRedisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~ {},{}",className,methodName);
            return proceed;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999,"系统错误");
    }
}
