package com.torrow.school.service;
import com.torrow.school.entity.TbCategory;

import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface TbResourceService {
    //张金高
    //得到所有资源类
    List<TbResource> selectAll();
    //得到单条资源类，目前用于概括类
    TbResource selectOne(Integer gId);
    //得到8条新闻，8条公告等,应用于首页
    void getResource(List<TbCategory> categoryList, Model model);
    //根据caId得到对应资源类，不分页
    List<TbResource> selectListByCaId(Integer caId);

    //为了查看一条信息
    TbResource selectByPrimaryKey(Integer id);
    //根据caId来进行查询
    TbResource selectByCaId(Integer caId);
    //修改
    int updateByPrimaryKey(TbResource record);
    //对资源类的分页查看
    public PageBean<TbResource>  findPage(int currentPage,int pageSize);
    //对资源类的部分数据进行分页查看
    public PageBean<TbResource>  findingByPaging(int currentPage,TbCategory record,int pageSize);//张金高用
    //根据id来删除资源类
    int deleteByPrimaryKey(Integer id);
    //插入资源类
    int insert(TbResource record);
    //根据caId来删除
    int deleteByCaId(Integer caId);
	//根据reTitle来查询
    public TbResource selectByReTitle(String reTitle);
    //根据reContent来查询
    public TbResource selectByReContent(String reContent);
    //上传图片
    public String uploadPicture(MultipartFile picture, String path) throws Exception;
    //上传文件
    public String uploadFile(MultipartFile file, String path) throws Exception;
    //文件的下载
    public void down(HttpServletRequest request,HttpServletResponse response,int id) throws Exception;
	
}