package com.kang.blog.service.impl;

import com.kang.blog.entity.ArticleBody;
import com.kang.blog.mapper.ArticleBodyMapper;
import com.kang.blog.service.ArticleBodyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.vo.ArticleBodyVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements ArticleBodyService {

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = baseMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
