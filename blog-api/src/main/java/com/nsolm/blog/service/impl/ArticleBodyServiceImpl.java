package com.nsolm.blog.service.impl;

import com.nsolm.blog.dao.mapper.ArticleBodyMapper;
import com.nsolm.blog.dao.pojo.ArticleBody;
import com.nsolm.blog.service.ArticleBodyService;
import com.nsolm.blog.vo.ArticleBodyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/5 16:45
 * @Description :
 */
@Service
public class ArticleBodyServiceImpl implements ArticleBodyService {
    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
