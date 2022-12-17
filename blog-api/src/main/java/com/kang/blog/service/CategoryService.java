package com.kang.blog.service;

import com.kang.blog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
public interface CategoryService extends IService<Category> {

    CategoryVo findCategoryById(Integer categoryId);

    Result findAll();
}
