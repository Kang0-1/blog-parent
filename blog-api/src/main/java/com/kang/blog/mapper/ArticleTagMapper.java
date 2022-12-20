package com.kang.blog.mapper;

import com.kang.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    List<Long> findHotsTagIdList(int limit);

    List<Long> findArticleIDListByTagId(Long tagId);
}
