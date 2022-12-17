package com.kang.blog.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class entityListToVOList {
    public static <T> List<T> EntityListToVOList(List<?> entityList, Class<T> clazz) {
        List<T> voList = new ArrayList<>(entityList.size());
        T target = null;
        for (Object object : entityList) {
            try {
                target = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            BeanUtils.copyProperties(object, target);
            voList.add(target);
        }
        return voList;
    }
}
