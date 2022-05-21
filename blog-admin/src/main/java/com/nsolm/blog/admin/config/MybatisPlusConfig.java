package com.nsolm.blog.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 11:21
 * @Description : MybatisPlus 配置类
 */

//注册到spring容器中
@Configuration
//扫描mapper
@MapperScan("com.nsolm.blog.admin.mapper")
public class MybatisPlusConfig {


    //集成分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;

    }




















}
