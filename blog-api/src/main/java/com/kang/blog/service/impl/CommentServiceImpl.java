package com.kang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kang.blog.entity.Comment;
import com.kang.blog.mapper.CommentMapper;
import com.kang.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.service.SysUserService;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Override
    public Result commentByArticleIdByStream(Long id) {
        //查出该文章所有评论
        List<Comment> list = baseMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getArticleId, id));
        List<Comment> commentList = list.stream().filter(comment -> comment.getLevel().equals("1"))
                .map(comment -> {
                    comment.setChildren(getChildren(comment, list));
                    return comment;
                })
                //TODO 时间排序
                .sorted((e1, e2) -> e1.getCreateDate() > e2.getCreateDate() ? 1 : 0)
                .collect(Collectors.toList());
        //TODO 将 Comment 全部转换成 CommentVo
        return Result.success(commentList);
    }

    private List<Comment> getChildren(Comment root, List<Comment> all) {
        List<Comment> children = all.stream().filter(comment -> comment.getParentId().equals(root.getId()))
                .map(comment -> {
                    comment.setChildren((getChildren(comment, all)));
                    return comment;
                })
                .sorted((e1, e2) -> e1.getCreateDate() > e2.getCreateDate() ? 1 : 0)
                .collect(Collectors.toList());

        return children;
    }

    @Override
    public Result commentByArticleId(Long articleId) {
        //查出该文章一级评论
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
            commentVo.setToUSer(userService.findUserVoById(comment.getToUid()));
        }

        return commentVo;
    }

    private List<CommentVo> findCommentByParentId(Long id) {
        List<Comment> commentList = baseMapper.selectList(new LambdaQueryWrapper<Comment>().eq(Comment::getParentId, id));
        return copyList(commentList);
    }
}
