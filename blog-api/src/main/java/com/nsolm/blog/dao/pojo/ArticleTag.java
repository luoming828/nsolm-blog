package com.nsolm.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 22:39
 * @Description :
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
