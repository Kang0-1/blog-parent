package com.kang.blog.vo.params;

import lombok.Data;

@Data
public class PageParams {
    private int page = 1;
    private int pageSize = 10;

    //展示某个分类或标签下的文章列表
    //  /listArticle接口下
    private Long categoryId;
    private Long tagId;

    //文章归档页面 发送的page参数
    private String year;
    private String month;

    public String getMonth() {
        if (this.month != null && this.month.length() == 1) {
            return "0" + this.month;
        }
        return this.month;
    }
}
