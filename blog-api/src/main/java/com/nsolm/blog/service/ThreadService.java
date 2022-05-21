package com.nsolm.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsolm.blog.dao.mapper.ArticleMapper;
import com.nsolm.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 14:17
 * @Description :
 */

@Component
public class ThreadService {

    @Resource
    private ArticleMapper articleMapper;


    @Async("taskExecutor")
    public void updateArticleViewCount(Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts+1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleUpdate,queryWrapper);
    }



}
