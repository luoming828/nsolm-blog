package com.nsolm.blog.vo.params;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 15:39
 * @Description :
 */

@Data
public class CommentParams {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
