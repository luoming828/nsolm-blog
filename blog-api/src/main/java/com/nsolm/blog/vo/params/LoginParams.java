package com.nsolm.blog.vo.params;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 10:45
 * @Description :
 */
@Data
public class LoginParams {

    private String account;
    private String password;
    private String nickname;
}
