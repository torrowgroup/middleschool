package com.torrow.school.service;

import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;

import java.util.List;

public interface TbResourceService {
    int deleteByPrimaryKey(Integer id);

    int insert(TbResource record);
    
    
    int deleteByCaId(Integer caId);
    
    //张金高
    //得到所有资源类
    List<TbResource> selectAll();
    //得到单条资源类，目前用于概括类
    TbResource selectOne();
    
    //为了查看一条信息
    TbResource selectByPrimaryKey(Integer id);
    //根据caId来进行查询
    TbResource selectByCaId(Integer caId);
    //修改
    int updateByPrimaryKey(TbResource record);
    //对资源类的分页查看
    public PageBean<TbResource>  findPage(int currentPage);
    //对资源类的部分数据进行分页查看
    public PageBean<TbResource>  findingByPaging(int currentPage,TbCategory record);
}