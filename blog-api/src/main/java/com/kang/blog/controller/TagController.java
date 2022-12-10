package com.kang.blog.controller;


import com.kang.blog.service.ArticleTagService;
import com.kang.blog.service.TagService;
import com.kang.blog.vo.R;
import com.kang.blog.vo.Result;
import com.kang.blog.vo.TagVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@RestController
@RequestMapping("tags")
public class TagController {

    @Resource
    private TagService tagService;

    @Resource
    private ArticleTagService articleTagService;



    @GetMapping("hot")
    public Result hot(){
        int limit=6;
        List<Long> tagIdList=articleTagService.hot(limit);
        if(tagIdList.isEmpty()){
            return Result.success(Collections.EMPTY_LIST);
        }
        List<TagVo> hotTagVoList=tagService.getTags(tagIdList);
        //return R.success().data("hotTagVoList",hotTagVoList);
        return Result.success(hotTagVoList);

    }

}

