package com.kang.blog.controller;


import com.kang.blog.service.CategoryService;
import com.kang.blog.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public Result categorys() {
        return categoryService.findAll();
    }

}

