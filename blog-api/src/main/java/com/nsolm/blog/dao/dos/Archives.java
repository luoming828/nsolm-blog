package com.nsolm.blog.dao.dos;

import lombok.Data;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/2 9:34
 * @Description : 也是从数据库查询出来的对象，但是不需要持久化
 */

@Data
public class Archives {
    private Integer year;
    private Integer month;
    private Long count;
}
