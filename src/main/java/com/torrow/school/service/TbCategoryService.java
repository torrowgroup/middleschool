package com.torrow.school.service;

import java.util.List;

import org.springframework.ui.Model;

import com.torrow.school.entity.TbCategory;
import com.torrow.school.util.PageBean;

public interface TbCategoryService {
	//张金高
    //得到全部类别类
    List<TbCategory> selectAll();
    //根据所有用户部门得到所有用户类别类 安李杰用
    List<TbCategory> selectByPid(List<Integer> pidList);
    //将学校概括，校园新闻，教育教研等封装进model,追加用户点击的功能项
    void getCategory(Integer rId,Model model);

    
	//删除类别信息
    int deleteByPrimaryKey(Integer id);
    //插入类别
    public void insert(TbCategory record);
    //根据id来查看类别信息
    TbCategory selectByPrimaryKey(Integer id);//张金高用
    //修改类别信息
    int updateByPrimaryKey(TbCategory record);
    //进行查询类别名称
	public TbCategory selectCaName(String caName);
	//类别信息分页显示
	public PageBean<TbCategory>  findPage(int currentPage,int pageSize);
	//通过传进去父id来查询信息安李杰加
	public List<TbCategory> queryByPid(int Pid);//张金高用
	//添加的跳转
	public void addBySelectPid(Model model,int Pid);

}