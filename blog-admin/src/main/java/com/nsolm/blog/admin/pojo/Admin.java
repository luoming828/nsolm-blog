package com.nsolm.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/14 14:25
 * @Description :
 */

@Data
public class Admin {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}
