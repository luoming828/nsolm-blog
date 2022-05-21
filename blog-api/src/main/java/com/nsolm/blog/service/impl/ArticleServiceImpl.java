package com.nsolm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsolm.blog.dao.dos.Archives;
import com.nsolm.blog.dao.mapper.ArticleBodyMapper;
import com.nsolm.blog.dao.mapper.ArticleMapper;
import com.nsolm.blog.dao.mapper.ArticleTagMapper;
import com.nsolm.blog.dao.pojo.Article;
import com.nsolm.blog.dao.pojo.ArticleBody;
import com.nsolm.blog.dao.pojo.ArticleTag;
import com.nsolm.blog.dao.pojo.SysUser;
import com.nsolm.blog.service.*;
import com.nsolm.blog.utils.UserThreadLocal;
import com.nsolm.blog.vo.ArticleVo;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.TagVo;
import com.nsolm.blog.vo.params.ArticleParams;
import com.nsolm.blog.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 13:26
 * @Description :
 */

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagService tagService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ArticleBodyService articleBodyService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page,pageParams.getCategoryId(),
                pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));

    }
    //@Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 1.分页查询 article 数据库表
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null){
//            // 加入标签 条件查询
//            // article表中 并没有tag字段 一篇文章有多个标签
//            // article_tag article_id 1:n tag_id
//            LambdaQueryWrapper<ArticleTag> queryWrapper1 = new LambdaQueryWrapper<>();
//            queryWrapper1.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper1);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size()>0)
//                // and id in(1,2,3)
//                queryWrapper.in(Article::getId,articleIdList);
//        }
//
//        // 是否置顶排序
//        // order by create_date desc
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(records,true,true);
//        return Result.success(articleVoList);
//    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // select id,title from article order by view_counts desc limit 5;
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList,false,false));

    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // select id,title from article order by CreateDate desc limit 5;
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList,false,false));
    }

    @Override
    public Result listArchives(int limit) {
        List<Archives> archivesList = articleMapper.listArchives();

        return Result.success(archivesList);
    }


    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH-mm"));
        //
        if(isTag){
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId));
        }
        return articleVo;

    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH-mm"));
        //
        if(isTag){
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId));
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(articleBodyService.findArticleBodyById(bodyId));

        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;

    }

    @Resource
    private ThreadService threadService;
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1.根据id查询文章信息
         * 2.根据bodyId和
         */
        Article article = articleMapper.selectById(articleId);
        if( article == null)
        {
            return Result.fail(-99,"no article");
        }
        ArticleVo articleVo = copy(article, true, true,true,true);

        //查看完文章,本应直接返回数据，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        //更新增加了 此次接口的耗时，如果一旦出了问题 不能影响 查看文章的操作
        //线程池 可以把更新操作扔到线程池中执行，和主线程就不相关了
        threadService.updateArticleViewCount(article);



        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParams articleParams) {
        /**
         * 1.作者ID
         * 2.标签 要将标签加入到关联表中
         */

        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParams.getTitle());
        article.setSummary(articleParams.getSummary());
        article.setViewCounts(0);
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(articleParams.getCategory().getId());
        articleMapper.insert(article);
        List<TagVo> tags = articleParams.getTags();
        if( tags != null){
            Long articleId = article.getId();
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());

        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentHtml(articleParams.getBody().getContentHtml());


        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());

        articleMapper.updateById(article);
        Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());
        return Result.success(map);
    }
}
