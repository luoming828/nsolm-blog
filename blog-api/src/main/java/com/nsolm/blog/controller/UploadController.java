package com.nsolm.blog.controller;

import com.nsolm.blog.utils.QiniuUtils;
import com.nsolm.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author: Luo Ming
 * @Date: 2022/5/9 14:52
 * @Description :
 */
@RestController
@RequestMapping("upload")
@Slf4j
public class UploadController {

    @Resource
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file){

        // 原始文件名称 比如 abc.png
        String originalFilename = file.getOriginalFilename();
        // 唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        // 上传到
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){

            return Result.success(QiniuUtils.url+ fileName);
        }
        return Result.fail(20001,"上传失败");
    }

}
