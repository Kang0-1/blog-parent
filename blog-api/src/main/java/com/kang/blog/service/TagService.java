package com.kang.blog.service;

import com.kang.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface TagService extends IService<Tag> {

    List<TagVo> findTagsByArticleId(Long articleId);


    List<TagVo> getTags(List<Long> tagIdList);
}
