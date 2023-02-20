package com.kang.blog.service.mq;

import com.alibaba.fastjson.JSON;
import com.kang.blog.service.ArticleService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.ArticleMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Set;

/**
 * @author 康
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "blog-update-article-group", topic = "blog-update-article")
public class ArticleListener implements RocketMQListener<ArticleMessage> {

    @Resource
    private ArticleService articleService;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(ArticleMessage articleMessage) {
        Long articleId = articleMessage.getArticleId();
        String redisKey = "view_article::ArticleController::findArticleById::" + DigestUtils.md5Hex(articleId.toString());
        Result result = articleService.getArticleById(articleId);
        //更新查看文章详情的缓存
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(result), Duration.ofMillis(5 * 60 * 1000));
        log.info("----更新了缓存----:{}", redisKey);
        //文章列表缓存  直接删除
        Set<String> keys = redisTemplate.keys("listArticle*");
        for (String key : keys) {
            redisTemplate.delete(key);
            log.info("删除了文章列表的缓存:{}", key);
        }
    }
}
