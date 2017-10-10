package com.torrow.school.service;

import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;

import java.util.List;

public interface TbResourceService {
    int deleteByPrimaryKey(Integer reId);

    int insert(TbResource record);

    TbResource selectByPrimaryKey(Integer reId);

    List<TbResource> selectAll();

    int updateByPrimaryKey(TbResource record);
    //对资源类的分页查看
    public PageBean<TbResource>  findPage(int currentPage);
}