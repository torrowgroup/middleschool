package com.torrow.school.service;

import com.torrow.school.entity.TbCategory;
import java.util.List;

public interface TbCategoryService {
    int deleteByPrimaryKey(Integer caId);

    int insert(TbCategory record);

    TbCategory selectByPrimaryKey(Integer caId);

    List<TbCategory> selectAll();

    int updateByPrimaryKey(TbCategory record);
}