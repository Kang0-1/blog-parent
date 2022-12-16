package com.kang.blog.service;

import com.kang.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.utils.Result;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface CommentService extends IService<Comment> {

    /**
     * 根据文章ID查询评论列表
     *
     * @param id
     * @return
     */
    Result commentByArticleId(Long id);


    Result commentByArticleIdByStream(Long id);
}
