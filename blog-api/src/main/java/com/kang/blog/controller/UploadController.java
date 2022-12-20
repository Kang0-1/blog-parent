package com.kang.blog.controller;

import com.kang.blog.common.aop.LogAnnotation;
import com.kang.blog.service.UploadService;
import com.kang.blog.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RequestMapping("upload")
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        return uploadService.uploadImg(file);
    }

}
