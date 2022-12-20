package com.kang.blog.service.impl;

import com.kang.blog.service.UploadService;
import com.kang.blog.utils.QiniuUtils;
import com.kang.blog.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    private QiniuUtils qiniuUtils;

    @Override
    public Result uploadImg(MultipartFile file) {
        //原始文件名称: 如aa.png
        String originalFilename = file.getOriginalFilename();

        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        //上传到七牛云服务器
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(20001, "上传失败");
    }
}
