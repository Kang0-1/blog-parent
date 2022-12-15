package com.kang.blog.vo;

import com.kang.blog.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private List<CategoryVo> category;

}
