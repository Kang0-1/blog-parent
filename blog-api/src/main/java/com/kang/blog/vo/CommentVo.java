package com.kang.blog.vo;

import com.kang.blog.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo {

    private Long id;
    private String content;
    private UserVo author;
    private List<CommentVo> children;
    private String createDate;
    private Integer level;
    private UserVo toUSer;
}
