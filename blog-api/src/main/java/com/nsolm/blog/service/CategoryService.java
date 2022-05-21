package com.nsolm.blog.service;

import com.nsolm.blog.vo.CategoryVo;
import com.nsolm.blog.vo.Result;

public interface CategoryService {

        CategoryVo findCategoryById(Long categoryId);

        Result findAll();

        Result findAllDetails();

        Result categoryDetailById(Long id);

}
