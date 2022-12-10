package com.kang.blog.mapper;

import com.kang.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.blog.entity.doS.Archives;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
