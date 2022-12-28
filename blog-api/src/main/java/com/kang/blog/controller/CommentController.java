package com.kang.blog.controller;


import com.kang.blog.service.CommentService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.params.CommentParams;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("article2/{id}")
    public Result comments2(@PathVariable Long id) {
        return commentService.commentByArticleId(id);
    }

    @GetMapping("article/{id}")
    public Result comments(@PathVariable Long id) {
        return commentService.commentByArticleIdByStream(id);
    }

    @PostMapping("/create/change")
    @Transactional
    public Result comment(@RequestBody CommentParams commentParams) {
        return commentService.comment(commentParams);
    }

}

