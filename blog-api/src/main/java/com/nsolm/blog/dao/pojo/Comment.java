package com.nsolm.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 14:53
 * @Description :
 */
@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}