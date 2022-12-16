package com.kang.blog.service.impl;

import com.kang.blog.entity.Category;
import com.kang.blog.mapper.CategoryMapper;
import com.kang.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public CategoryVo findCategoryById(Integer categoryId) {
        Category category = baseMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
