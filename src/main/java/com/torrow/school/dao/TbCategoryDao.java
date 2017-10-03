package com.torrow.school.dao;

import com.torrow.school.entity.TbCategory;
import java.util.List;


public interface TbCategoryDao {
    int deleteByPrimaryKey(Integer caId);

    int insert(TbCategory record);

    TbCategory selectByPrimaryKey(Integer caId);

    List<TbCategory> selectAll();

    int updateByPrimaryKey(TbCategory record);
}