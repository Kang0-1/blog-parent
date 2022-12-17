package com.kang.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo {

    private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> children;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private String level;
    private UserVo toUser;
    private Long parentId;
}
