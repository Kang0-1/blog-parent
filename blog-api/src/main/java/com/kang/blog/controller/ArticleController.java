package com.kang.blog.controller;


import com.kang.blog.entity.doS.Archives;
import com.kang.blog.service.ArticleService;
import com.kang.blog.vo.ArticleVo;

import com.kang.blog.vo.Result;
import com.kang.blog.vo.params.PageParams;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     * @throws Exception
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) throws Exception {
        List<ArticleVo> list = articleService.listArticle(pageParams);
        //return R.success().data("list",list);
        return Result.success(list);
    }

    /**
     * 首页 最热文章
     * @return
     * @throws Exception
     */
    @PostMapping("hot")
    public Result hotArticle() throws Exception {
        int limit=5;
        List<ArticleVo> articleVoList=articleService.hotArticle(limit);
        return Result.success(articleVoList);
    }

    /**
     * 首页 最新文章
     * @return
     * @throws Exception
     */
    @PostMapping("new")
    public Result newArticle() throws Exception {
        int limit=5;
        List<ArticleVo> articleVoList=articleService.newArticle(limit);
        return Result.success(articleVoList);
    }

    /**
     * 首页 文章归档
     * @return
     * @throws Exception
     */
    @PostMapping("listArchives")
    public Result listArchives() throws Exception {
        List<Archives> articleVoList=articleService.listArchives();
        return Result.success(articleVoList);
    }

}

