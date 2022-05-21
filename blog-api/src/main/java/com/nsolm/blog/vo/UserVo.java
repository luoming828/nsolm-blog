package com.nsolm.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 15:09
 * @Description :
 */
@Data
public class UserVo {

    private String nickname;

    private String avatar;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
}
