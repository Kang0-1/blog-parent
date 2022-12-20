package com.kang.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.kang.blog.entity.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer commentCounts;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer weight;

    private UserVo author;

    private ArticleBodyVo body;

    private CategoryVo category;

    private List<TagVo> tags;

}
