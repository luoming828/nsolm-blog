package com.nsolm.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nsolm.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 15:14
     *@Description : 根据文章id查询标签列表
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     *
     *@Author: Luo Ming
     *@Date: 2022/5/1 16:47
     *@Description : 查询最热标签 前limit条
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
