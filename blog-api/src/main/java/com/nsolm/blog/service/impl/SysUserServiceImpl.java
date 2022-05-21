package com.nsolm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsolm.blog.dao.mapper.SysUserMapper;
import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.service.LoginService;
import com.nsolm.blog.service.SysUserService;
import com.nsolm.blog.vo.ErrorCode;
import com.nsolm.blog.vo.LoginUserVo;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 15:37
 * @Description :
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private LoginService loginService;

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("用户不见啦");
            sysUser.setAvatar("/static/img/log.b3a48c0.png");
            sysUser.setId(1L);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public SysUser findUserById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("用户不见啦");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     *
     * @param token
     * @return
     *
     * 1.token 合法性校验 是否为空,解析是否成功，redis是否存在
     * 2.如果校验失败，返回错误
     * 3.如果成功，返回对应结果 LoginUserVo
     */
    @Override
    public Result findUserByToken(String token) {

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }



    @Override
    public void save(SysUser sysUser) {
        //这个地方 默认生成的id是分布式id 采用雪花算法
        sysUserMapper.insert(sysUser);
    }
}
