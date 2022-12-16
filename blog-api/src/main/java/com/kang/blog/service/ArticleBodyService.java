package com.kang.blog.service;

import com.kang.blog.entity.ArticleBody;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.vo.ArticleBodyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface ArticleBodyService extends IService<ArticleBody> {

    ArticleBodyVo findArticleBodyById(Long bodyId);
}
