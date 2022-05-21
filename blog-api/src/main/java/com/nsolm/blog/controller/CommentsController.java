package com.nsolm.blog.controller;

import com.nsolm.blog.service.CommentsService;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.CommentParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 14:56
 * @Description :
 */

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Resource
    private CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParams commentParams){
        return  commentsService.comment(commentParams);
    }

}
