package com.nsolm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsolm.blog.dao.mapper.CommentsMapper;
import com.nsolm.blog.dao.pojo.Comment;
import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.service.CommentsService;
import com.nsolm.blog.service.SysUserService;
import com.nsolm.blog.utils.UserThreadLocal;
import com.nsolm.blog.vo.CommentVo;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.UserVo;
import com.nsolm.blog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 15:00
 * @Description :
 */

@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1.根据文章id查询评论列表 从comments表中查询
         * 2.根据作者的id查询作者的信息
         * 3.判断如果 level == 1 要去查询它有没子评论
         * 4.如果有 根据评论id查询 （parent_id）
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);

        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);

    }

    @Override
    public Result comment(CommentParams commentParams) {
        // 获取当前登录用户
        SysUser sysUser = UserThreadLocal.get();

        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentsMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {

        List<CommentVo>  commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;

    }

    private CommentVo copy(Comment comment) {

        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        UserVo userVo = sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);
        Integer level = comment.getLevel();
        if( level == 1){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return  commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getParentId,id);
        lambdaQueryWrapper.eq(Comment::getLevel,2);
        List<Comment> commentList = commentsMapper.selectList(lambdaQueryWrapper);
        return copyList(commentList);
    }
}
