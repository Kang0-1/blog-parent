package com.kang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.blog.entity.Article;
import com.kang.blog.entity.doS.Archives;
import com.kang.blog.mapper.ArticleMapper;
import com.kang.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.service.SysUserService;
import com.kang.blog.service.TagService;
import com.kang.blog.vo.ArticleVo;
import com.kang.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private TagService tagService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());

        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        baseMapper.selectPage(page,queryWrapper);  // new QueryWrapper<Article>().orderByDesc("create_date")

        List<Article> articleList = page.getRecords();
        List<ArticleVo> articleVoList=articleList.stream().map(article -> {
            ArticleVo articleVo=new ArticleVo();
            BeanUtils.copyProperties(article,articleVo);
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
            articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId()).getNickname());

            return articleVo;
        }).collect(Collectors.toList());

        return articleVoList;
    }

    @Override
    public List<ArticleVo> hotArticle(int limit) {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().
                orderByDesc(Article::getViewCounts).
                select(Article::getId, Article::getTitle)
                .last("limit " + limit));

        List<ArticleVo> articleVoList=articles.stream().map(article -> {
            ArticleVo articleVo=new ArticleVo();
            BeanUtils.copyProperties(article,articleVo);
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
            return articleVo;
        }).collect(Collectors.toList());

        return articleVoList;
    }

    @Override
    public List<ArticleVo> newArticle(int limit) {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().
                orderByDesc(Article::getCreateDate).
                select(Article::getId, Article::getTitle)
                .last("limit " + limit));

        List<ArticleVo> articleVoList=articles.stream().map(article -> {
            ArticleVo articleVo=new ArticleVo();
            BeanUtils.copyProperties(article,articleVo);
            //articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
            return articleVo;
        }).collect(Collectors.toList());

        return articleVoList;
    }

    @Override
    public List<Archives> listArchives() {
        return baseMapper.listArchives();
    }
}
