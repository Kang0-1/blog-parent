package com.kang.blog.service;

import com.kang.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface ArticleTagService extends IService<ArticleTag> {

    List<Long> hot(int limit);

}
