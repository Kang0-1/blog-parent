package com.kang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kang.blog.entity.Article;
import com.kang.blog.entity.Comment;
import com.kang.blog.entity.SysUser;
import com.kang.blog.mapper.ArticleMapper;
import com.kang.blog.mapper.CommentMapper;
import com.kang.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.service.SysUserService;
import com.kang.blog.utils.Result;
import com.kang.blog.utils.UserThreadLocal;
import com.kang.blog.vo.CommentVo;
import com.kang.blog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private SysUserService userService;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Result commentByArticleIdByStream(Long id) {
        //查出该文章所有评论
        List<Comment> list = baseMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getArticleId, id));
        //转换成CommentVoList
        List<CommentVo> commentVoList = POList2VOList(list);
        //System.out.println(commentVoList);

        List<CommentVo> result = commentVoList.stream().filter(commentVo -> commentVo.getParentId().toString().equals("0"))
                .map(commentVo -> {
                    commentVo.setChildren(getChildren(commentVo, commentVoList));
                    return commentVo;
                })
                .sorted(Comparator.comparing(CommentVo::getCreateDate).reversed()) // 时间降序
                .collect(Collectors.toList());
        return Result.success(result);
    }

    private List<CommentVo> POList2VOList(List<Comment> list) {
        List<CommentVo> voList = new ArrayList<>();
        for (Comment comment : list) {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comment, commentVo);
            commentVo.setAuthor(userService.findUserVoById(comment.getAuthorId()));
            commentVo.setToUser(userService.findUserVoById(comment.getToUid()));
            voList.add(commentVo);
        }
        return voList;
    }

    private List<CommentVo> getChildren(CommentVo root, List<CommentVo> all) {
        List<CommentVo> children = all.stream().filter(commentVo -> commentVo.getParentId().equals(root.getId()) && commentVo.getToUser().getId().equals(root.getAuthor().getId()))
                .map(commentVo -> {
                    commentVo.setChildren((getChildren(commentVo, all)));
                    return commentVo;
                })
                .sorted(Comparator.comparing(CommentVo::getCreateDate).reversed())
                .collect(Collectors.toList());
        return children;
    }


    @Override
    public Result commentByArticleId(Long articleId) {
        List<Comment> commentList = baseMapper.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getArticleId, articleId)
                .eq(Comment::getLevel, 1));
        List<CommentVo> commentVoList = copyList(commentList);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);

        commentVo.setAuthor(userService.findUserVoById(comment.getAuthorId()));
        commentVo.setContent(comment.getContent());
        if (1 == Integer.parseInt(comment.getLevel())) { //一级评论查找子评论
            commentVo.setChildren(findCommentByParentId(comment.getId()));
        }
        if (1 < Integer.parseInt(comment.getLevel())) { //二级评论查找评论人
            commentVo.setToUser(userService.findUserVoById(comment.getToUid()));
        }
        return commentVo;
    }

    private List<CommentVo> findCommentByParentId(Long id) {
        List<Comment> commentList = baseMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getParentId, id));
        return copyList(commentList);
    }

    @Override
    public Result comment(CommentParams commentParams) {
        SysUser user = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(new Date());
        Long parentId = commentParams.getParentId();
        if (parentId == null || parentId == 0) {
            comment.setLevel("1");
        } else {
            comment.setLevel("2");
        }
        comment.setParentId(parentId == null ? 0 : parentId);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);

        Article articleForUpdate = articleMapper.selectById(commentParams.getArticleId());
        articleForUpdate.setCommentCounts(articleForUpdate.getCommentCounts() + 1);
        articleMapper.updateById(articleForUpdate);

        baseMapper.insert(comment);
        return Result.success(null);
    }
}
