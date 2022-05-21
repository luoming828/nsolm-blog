package com.nsolm.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 10:33
 * @Description :
 * 1.  首先，前端通过Web表单将自己的用户名和密码发送到后端的接口，这个过程一般是一个POST请求。
 * 建议的方式是通过SSL加密的传输(HTTPS)，从而避免敏感信息被嗅探
 * 2.  后端核对用户名和密码成功后，将包含用户信息的数据作为JWT的Payload，
 * 将其与JWT Header分别进行Base64编码拼接后签名，形成一个JWT Token，
 * 形成的JWT Token就是一个如同lll.zzz.xxx的字符串
 *
 * 3.JWT包含三个部分 标头(Header)、有效载荷(Payload)和签名(Signature)
 * 在传输的时候，会将JWT的3部分分别进行签发算法编码后用.进行连接形成最终传输的字符串
 */

public class JWTUtils {
    //密钥
    private static final String jwtToken = "123456nsolm!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        // 将用户id保存到body中
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            // 解析token
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}
