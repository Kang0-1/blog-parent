package com.kang.blog.vo.params;

import lombok.Data;

@Data
public class CommentParams {

    private Integer articleId;

    private String content;

    private Long parentId;

    private Long toUserId;
}
