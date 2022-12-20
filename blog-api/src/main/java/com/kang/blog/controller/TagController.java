package com.kang.blog.controller;


import com.kang.blog.service.ArticleTagService;
import com.kang.blog.service.TagService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.TagVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
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
@RequestMapping("tags")
public class TagController {

    @Resource
    private TagService tagService;

    @Resource
    private ArticleTagService articleTagService;

    @GetMapping
    public Result tags() {
        return tagService.findAll();
    }


    @GetMapping("hot")
    public Result hot() {
        int limit = 6;
        List<Long> tagIdList = articleTagService.hot(limit);
        if (tagIdList.isEmpty()) {
            return Result.success(Collections.EMPTY_LIST);
        }
        List<TagVo> hotTagVoList = tagService.getTags(tagIdList);
        //return R.success().data("hotTagVoList",hotTagVoList);
        return Result.success(hotTagVoList);
    }

    /**
     * 返回全部tag的所有信息
     *
     * @return
     */
    @GetMapping("detail")
    public Result tagDetail() {
        return tagService.findAllDetail();
    }

    /**
     * 返回某一个tag的所有信息
     *
     * @return
     */
    @GetMapping("detail/{id}")
    public Result tagDetailById(@PathVariable("id") Long tagId) {
        return tagService.findDetailById(tagId);
    }

}

