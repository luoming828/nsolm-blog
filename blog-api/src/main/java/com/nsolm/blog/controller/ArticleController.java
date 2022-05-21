package com.nsolm.blog.controller;

import com.nsolm.blog.common.aop.LogAnnotation;
import com.nsolm.blog.common.cache.Cache;
import com.nsolm.blog.service.ArticleService;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.ArticleParams;
import com.nsolm.blog.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 12:26
 * @Description :
 */


@RestController
@RequestMapping("/articles")
public class ArticleController {


    @Resource
    private ArticleService articleService;

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 13:21
     *@Description : 首页文章列表
     */
    @PostMapping
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    //@Cache(expire = 5*60*1000,name = "list-Article")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 17:18
     *@Description : 首页最热文章
     */
    @PostMapping("/hot")
    @Cache(expire = 5*60*1000,name = "hot-article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    @PostMapping("/new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    @PostMapping("/listArchives")
    public Result listArticles(){
        int limit = 5;
        return articleService.listArchives(limit);
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParams articleParams){
        return articleService.publish(articleParams);
    }

    @PostMapping("{id}")
    public Result articleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
}
