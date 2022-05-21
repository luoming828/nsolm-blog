package com.nsolm.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/13 15:06
 * @Description :
 */

@Data
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String description;
}
