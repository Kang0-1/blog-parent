package com.kang.blog.service;

import com.kang.blog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.entity.doS.Archives;
import com.kang.blog.vo.ArticleVo;
import com.kang.blog.vo.params.PageParams;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface ArticleService extends IService<Article> {

    List<ArticleVo> listArticle(PageParams pageParams) throws InvocationTargetException, IllegalAccessException;

    List<ArticleVo> hotArticle(int limit);

    List<ArticleVo> newArticle(int limit);

    List<Archives> listArchives();
}
