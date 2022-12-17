package com.kang.blog.service.impl;

import com.kang.blog.entity.Tag;
import com.kang.blog.mapper.TagMapper;
import com.kang.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.blog.utils.Result;
import com.kang.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kang
 * @since 2022-10-31
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    public TagVo copy(Tag tag){
        TagVo tagVo=new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList =new ArrayList<>();
        for(Tag tag:tagList){
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    /**
     * 通过 ArticleId 查找对应 tag 并返回 tagVo
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tagList= baseMapper.findTagsByArticleId(articleId);
        return copyList(tagList);
    }

    /**
     * 根据 tagId 获取 tagVo 返回
     * @param tagIdList
     * @return
     */
    @Override
    public List<TagVo> getTags(List<Long> tagIdList) {
        //List<Tag> tagList =baseMapper.findTagsByTagId(tagIdList);
        List<Tag> tagList = baseMapper.selectBatchIds(tagIdList);
        return copyList(tagList);
    }

    @Override
    public Result findAll() {
        List<Tag> tagList = baseMapper.selectList(null);
        return Result.success(copyList(tagList));
    }


}
