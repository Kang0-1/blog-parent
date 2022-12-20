package com.kang.blog.vo.params;

import com.kang.blog.vo.CategoryVo;
import com.kang.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParams {

    private Long id;

    private ArticleBodyParams body; //body->{content contentHtml}

    private CategoryVo category; // category->{ id avatar categoryName}

    private String summary;

    private List<TagVo> tags;

    private String title;

}
