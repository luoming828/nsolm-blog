package com.nsolm.blog.controller;

import com.nsolm.blog.service.CategoryService;
import com.nsolm.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 21:03
 * @Description :
 */

@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }

    @GetMapping("/detail")
    public Result categoriesDetails(){
        return categoryService.findAllDetails();
    }

    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id){
        return categoryService.categoryDetailById(id);
    }
}
