package com.torrow.school.service;

import com.torrow.school.entity.TbCategory;
import com.torrow.school.util.PageBean;

import java.util.List;

public interface TbCategoryService {
	//删除类别信息
    int deleteByPrimaryKey(Integer id);
    //插入类别
    public void insert(TbCategory record);
    //根据id来查看类别信息
    TbCategory selectByPrimaryKey(Integer id);
    
    //张金高
    //得到全部类别类
    List<TbCategory> selectAll();
    //修改类别信息
    int updateByPrimaryKey(TbCategory record);
    //进行查询类别名称
	public TbCategory selectCaName(String caName);
	//类别信息分页显示
	public PageBean<TbCategory>  findPage(int currentPage);
}