package com.kang.blog.controller;


import com.kang.blog.common.aop.LogAnnotation;
import com.kang.blog.entity.doS.Archives;
import com.kang.blog.service.ArticleService;
import com.kang.blog.vo.ArticleVo;

import com.kang.blog.utils.Result;
import com.kang.blog.vo.params.ArticleParams;
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
    // 加此注解 代表要对此接口记录日志
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams) throws Exception {
        return articleService.listArticle(pageParams);  //return R.success().data("list",list);
    }

    @PostMapping("listArticleByXML")
    // 加此注解 代表要对此接口记录日志
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    public Result listArticleByXML(@RequestBody PageParams pageParams) {
        return articleService.listArticleByXML(pageParams);
    }

    /**
     * 首页 最热文章
     *
     * @return
     * @throws Exception
     */
    @PostMapping("hot")
    public Result hotArticle() throws Exception {
        int limit = 5;
        List<ArticleVo> articleVoList = articleService.hotArticle(limit);
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
        List<Archives> articleVoList = articleService.listArchives();
        return Result.success(articleVoList);
    }

    /**
     * 查看文章并通过线程池修改阅读量
     *
     * @param id
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable Long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParams articleParams) {
        return articleService.publish(articleParams);
    }

}

