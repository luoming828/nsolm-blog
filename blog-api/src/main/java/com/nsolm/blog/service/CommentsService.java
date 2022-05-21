package com.nsolm.blog.service;

import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.CommentParams;

public interface CommentsService {
    Result commentsByArticleId(Long id);

    Result comment(CommentParams commentParams);
}
