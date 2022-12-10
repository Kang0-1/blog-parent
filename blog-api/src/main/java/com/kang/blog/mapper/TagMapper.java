package com.kang.blog.mapper;

import com.kang.blog.entity.Tag;
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
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long articleId);



    List<Tag> findTagsByTagId(List<Long> tagIdList);
}
