package com.nsolm.blog.service.impl;


import com.alibaba.fastjson.JSON;
import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.service.LoginService;
import com.nsolm.blog.service.SysUserService;
import com.nsolm.blog.utils.JWTUtils;
import com.nsolm.blog.vo.ErrorCode;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 10:43
 * @Description :
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private SysUserService sysUserService;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //加密盐
    private static final String salt = "nsolm!@#";
    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/2 10:49
     *@Description : 登录功能实现
     * 1.检验用户名密码是否合法
     * 2.根据用户名和密码 到user表中查询
     * 3.如果不存在 则登录失败
     * 4.若不存在 则使用jwt 生成token 返回给前端
     * 5.token 放入redis中 redis token:user 信息 设置过期时间
     * （登录认证的时候先认证token字符串是否合法，再去redis认证是否存在）
     */
    @Override
    public Result login(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password))
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        password = DigestUtils.md5Hex(password+ salt);
        SysUser user = sysUserService.findUser(account,password);
        if (user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(user.getId());
        // 将用户数据 存入redis中
        stringRedisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return null;
        }
        // 从redis中获取用户数据
        String userJson = stringRedisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }

    @Override
    public Result logout(String token) {
        stringRedisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/5 14:30
     *@Description : 注册
     * 1.判断参数是否合法
     * 2.判断账户是否已存在 存在返回 账户已经被注册
     * 3.如果不存在 注册用户
     * 4.生成token
     * 5.存入redis 并返回
     * 6.加上事务 一旦中间的任何过程出现问题 注册的用户需要回滚
     * 7.
     */
    @Override
    public Result register(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if( StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);

        if( sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setAccount(account);
        sysUser.setNickname(nickname);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        stringRedisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

        return Result.success(token);
    }

}
