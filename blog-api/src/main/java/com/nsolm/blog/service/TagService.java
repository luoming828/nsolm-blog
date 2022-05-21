package com.nsolm.blog.service;

import com.nsolm.blog.dao.pojo.Tag;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
