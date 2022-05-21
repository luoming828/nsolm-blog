package com.nsolm.blog.vo;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 16:31
 * @Description :
 */

@Data
public class LoginUserVo {
    private Long id;

    private String account;

    private String nickname;

    private String avatar;
}
