package com.nsolm.blog.vo;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/5 16:33
 * @Description :
 */

@Data
public class CategoryVo {

    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
