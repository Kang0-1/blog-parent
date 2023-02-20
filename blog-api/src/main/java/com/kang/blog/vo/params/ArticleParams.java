package com.kang.blog.vo.params;

import com.kang.blog.vo.CategoryVo;
import com.kang.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author 康
 */
@Data
public class ArticleParams {

    private Long id;

    /**
     * body->{content contentHtml}
     */
    private ArticleBodyParams body;

    /**
     * category->{ id avatar categoryName}
     */
    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

}
