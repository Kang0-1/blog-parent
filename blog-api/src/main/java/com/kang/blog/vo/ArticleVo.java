package com.kang.blog.vo;

import com.kang.blog.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Long id;

    private Integer commentCounts;

    private String createDate;

    private String summary;

    private String title;

    private Integer viewCounts;

    private Integer weight;

    private String author;

    private ArticleBodyVo body;

    private CategoryVo category;

    private List<TagVo> tags;

}
