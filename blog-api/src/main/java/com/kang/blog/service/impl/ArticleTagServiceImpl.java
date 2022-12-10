package com.kang.blog.service.impl;

import com.kang.blog.entity.ArticleTag;
import com.kang.blog.mapper.ArticleTagMapper;
import com.kang.blog.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public List<Long> hot(int limit) {
        return baseMapper.findHotsTagIdList(limit);
    }
}
