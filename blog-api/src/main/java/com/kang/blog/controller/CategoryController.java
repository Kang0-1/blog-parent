package com.kang.blog.controller;


import com.kang.blog.service.CategoryService;
import com.kang.blog.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 传回分类的id和名称
     *
     * @return
     */
    @GetMapping
    public Result category() {
        return categoryService.findAll();
    }

    /**
     * 展示全部分类的信息
     *
     * @return
     */
    @GetMapping("detail")
    public Result categoryDetail() {
        return categoryService.findAllDetail();
    }

    /**
     * 展示某一分类的图片，介绍
     *
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id) {
        return categoryService.findDetailById(id);
    }

}

