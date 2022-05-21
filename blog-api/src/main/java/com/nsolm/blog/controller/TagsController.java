package com.nsolm.blog.controller;

import com.nsolm.blog.service.TagService;
import com.nsolm.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 16:23
 * @Description :
 */

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Resource
    private TagService tagService;

    @GetMapping("/hot")
    public Result hot(){
        int limit = 6;
        return tagService.hots(limit);
    }

    @GetMapping
    public Result tags(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result tagsAllDetail(){
        return tagService.findAllDetail();
    }


    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id ){

        return tagService.findDetailById(id);
    }
}





