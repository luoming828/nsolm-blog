package com.nsolm.blog.service;

import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.params.ArticleParams;
import com.nsolm.blog.vo.params.PageParams;

public interface ArticleService {

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 13:25
     *@Description : 分页查询文章列表
     */

    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/2 9:28
     *@Description : 文章归档
     */
    Result listArchives(int limit);

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/5 16:25
     *@Description : 查询文章
     */
    Result findArticleById(Long articleId);

    Result publish(ArticleParams articleParams);
}
