package com.kang.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kang.blog.entity.Article;
import com.kang.blog.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ThreadService {

    @Resource
    private ArticleMapper articleMapper;

    @Async("taskExecutor")
    public void updateViewCount(Article article) {
        Article articleForUpdate = new Article();
        articleForUpdate.setViewCounts(article.getViewCounts() + 1);
        articleMapper.update(articleForUpdate, new LambdaUpdateWrapper<Article>()
                .eq(Article::getId, article.getId())
                //乐观锁(CAS),查询文章时对应一个ViewCount，修改时查询ViewCount，如果没被修改，则对其修改
                // update article set viewCount=100 where vieCount=99 and id=1
                .eq(Article::getViewCounts, article.getViewCounts()));
        try {
            Thread.sleep(1);
            System.out.println("阅读量更新完成...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
