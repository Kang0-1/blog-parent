package com.kang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kang.blog.entity.Article;
import com.kang.blog.entity.ArticleBody;
import com.kang.blog.entity.ArticleTag;
import com.kang.blog.entity.SysUser;
import com.kang.blog.entity.doS.Archives;
import com.kang.blog.mapper.ArticleBodyMapper;
import com.kang.blog.mapper.ArticleMapper;
import com.kang.blog.mapper.ArticleTagMapper;
import com.kang.blog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.utils.UserThreadLocal;
import com.kang.blog.vo.ArticleVo;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.TagVo;
import com.kang.blog.vo.params.ArticleParams;
import com.kang.blog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private ArticleBodyMapper articleBodyMapper;

    @Resource
    private ArticleBodyService articleBodyService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private ThreadService threadService;


    @Override
    public Result listArticleByXML(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticleByXML(page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(PojoToVoUtil(records));
    }

    /**
     * 首页文章查询(分页查询)
     *
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId() != null) {
            // and category_id=#{category_id}
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }
        if (pageParams.getTagId() != null) {
            // ms_article表中没有tag字段，多对多关系，存放在 ms_article_tag中
            // 这里需要根据tagId查出对应文章的id列表
            // 再在ms_article中使用 in(idList)SQL语句查询
            List<Long> articleIDListByTagId = articleTagMapper.findArticleIDListByTagId(pageParams.getTagId());
            queryWrapper.in(Article::getId, articleIDListByTagId);
        }

        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        baseMapper.selectPage(page, queryWrapper);  // new QueryWrapper<Article>().orderByDesc("create_date")

        List<Article> articleList = page.getRecords();
        return Result.success(PojoToVoUtil(articleList));
    }

    public List<ArticleVo> PojoToVoUtil(List<Article> articleList) {
        return articleList.stream().map(article -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
            articleVo.setAuthor(sysUserService.findUserVoById(article.getAuthorId()));
            return articleVo;
        }).collect(Collectors.toList());
    }

    /**
     * 最热文章接口
     *
     * @param limit
     * @return
     */
    @Override
    public List<ArticleVo> hotArticle(int limit) {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().
                orderByDesc(Article::getViewCounts).
                select(Article::getId, Article::getTitle)
                .last("limit " + limit));

        List<ArticleVo> articleVoList=articles.stream().map(article -> {
            ArticleVo articleVo=new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVo.setCreateDate(article.getCreateDate());
            return articleVo;
        }).collect(Collectors.toList());

        return articleVoList;
    }

    /**
     * 最新文章接口
     * @param limit
     * @return
     */
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

    /**
     * 文章归档(按日期)
     * @return
     */
    @Override
    public List<Archives> listArchives() {
        return articleMapper.listArchives();
    }

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setAuthor(sysUserService.findUserVoById(article.getAuthorId()));
        articleVo.setBody(articleBodyService.findArticleBodyById(article.getBodyId()));
        articleVo.setCategory(categoryService.findCategoryById(article.getCategoryId()));
        articleVo.setTags(tagService.findTagsByArticleId(articleVo.getId()));

        threadService.updateViewCount(article);

        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParams articleParams) {
        // 发布文章时需要登录，所以加入拦截器接口进行拦截
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        //插入 ms_article表的基本数据
        article.setAuthorId(sysUser.getId());
        article.setWeight(0);
        article.setViewCounts(0);
        article.setCreateDate(new Date());
        article.setCommentCounts(0);
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());
        article.setCategoryId(articleParams.getCategory().getId());
        articleMapper.insert(article);
        //插入后会自动生成文章ID
        Long articleId = article.getId();

        // 插入ms_article_tag表的数据
        List<TagVo> tags = articleParams.getTags();
        if (null != tags) {
            for (TagVo tagVo : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tagVo.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        //插入ms_article_body表的数据
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentHtml(articleParams.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        Map<String, String> map = new HashMap<>();
        map.put("id", articleId.toString());
        return Result.success(map);
    }
}
