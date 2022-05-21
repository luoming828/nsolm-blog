package com.nsolm.blog.vo.params;

import com.nsolm.blog.dao.pojo.Category;
import com.nsolm.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/8 21:39
 * @Description :
 */

@Data
public class ArticleParams {
    private Long id ;

    private ArticleBodyParams body;

    private Category category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
