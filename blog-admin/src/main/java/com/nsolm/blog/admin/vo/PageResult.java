package com.nsolm.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/13 15:09
 * @Description :
 */

@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}
