package com.nsolm.blog.admin.model.params;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/13 15:02
 * @Description :
 */

@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
