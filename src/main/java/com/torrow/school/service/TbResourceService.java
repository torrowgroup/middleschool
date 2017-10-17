package com.torrow.school.service;

import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;

import java.util.List;

public interface TbResourceService {
    int deleteByPrimaryKey(Integer id);

    int insert(TbResource record);
    
    
    int deleteByCaId(Integer caId);
    
    //为了查看一条信息
    TbResource selectByPrimaryKey(Integer id);

    //张金高
    //得到所有资源类
    List<TbResource> selectAll();
    
    
    //根据caId来进行查询
    TbResource selectByCaId(Integer caId);
    
    //修改
    int updateByPrimaryKey(TbResource record);
    //对资源类的分页查看
    public PageBean<TbResource>  findPage(int currentPage);
    
    //对资源类的部分数据进行分页查看
    public PageBean<TbResource>  findingByPaging(int currentPage,int caPid);
}