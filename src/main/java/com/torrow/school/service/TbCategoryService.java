package com.torrow.school.service;

import com.torrow.school.entity.TbCategory;
import java.util.List;

public interface TbCategoryService {
    int deleteByPrimaryKey(Integer caId);
    //插入类别
    public void insert(TbCategory record);

    TbCategory selectByPrimaryKey(Integer caId);

    List<TbCategory> selectAll();

    int updateByPrimaryKey(TbCategory record);
    //进行查询类别名称
	public TbCategory selectCaName(String caName);
	public TbCategory  findPage(int currentPage);
}