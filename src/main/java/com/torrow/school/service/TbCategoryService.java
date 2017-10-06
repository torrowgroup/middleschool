package com.torrow.school.service;

import com.torrow.school.entity.TbCategory;
import java.util.List;

public interface TbCategoryService {
    int deleteByPrimaryKey(Integer caId);

    int insert(TbCategory record);

    TbCategory selectByPrimaryKey(Integer caId);
    
    //张金高
    //得到全部类别类
    List<TbCategory> selectAll();

    int updateByPrimaryKey(TbCategory record);
}