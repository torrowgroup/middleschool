package com.torrow.school.service;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;
import java.util.List;

public interface TbResourceService {
    //张金高
    //得到所有资源类
    List<TbResource> selectAll();
    //得到单条资源类，目前用于概括类
    TbResource selectOne(Integer gId);

    //为了查看一条信息
    TbResource selectByPrimaryKey(Integer id);
    //根据caId来进行查询
    TbResource selectByCaId(Integer caId);
    //修改
    int updateByPrimaryKey(TbResource record);
    //对资源类的分页查看
    public PageBean<TbResource>  findPage(int currentPage);
    //对资源类的部分数据进行分页查看
    public PageBean<TbResource>  findingByPaging(int currentPage,TbCategory record);//张金高用
    //根据id来删除资源类
    int deleteByPrimaryKey(Integer id);
    //插入资源类
    int insert(TbResource record);
    //根据caId来删除
    int deleteByCaId(Integer caId);
}