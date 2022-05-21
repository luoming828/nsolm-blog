package com.nsolm.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nsolm.blog.dao.mapper.TagMapper;
import com.nsolm.blog.dao.pojo.Tag;
import com.nsolm.blog.service.TagService;
import com.nsolm.blog.vo.Result;
import com.nsolm.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/1 15:05
 * @Description :
 */

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;
    
    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 16:29
     *@Description : 通过tag_article 关联表查询 一个文章所包含的标签
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // MybatisPlus 无法进行多表查询
        List<Tag> tagList = tagMapper.findTagsByArticleId(articleId);
        return copyList(tagList);
    }

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 16:29
     *@Description : 查询最热标签
     * 1.拥有文章数量最多的标签
     * 2.查询 根据tag_id 分组 计数，从大到小 排序 取前 limit 个
     */
    @Override
    public Result hots(int limit) {
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        // select * from tag where id in (1,2,3,4)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }

        return tagVoList;
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
}
