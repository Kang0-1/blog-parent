package com.kang.blog.service;

import com.kang.blog.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    Result uploadImg(MultipartFile file);

}
