package com.torrow.school.service;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;

public interface TbResourceService {
    //张金高
    //得到所有资源类
    List<TbResource> selectAll();
    //得到单条资源类，目前用于概括类,根据caId得到
    TbResource selectOne(Integer gId);
    //得到8条新闻，8条公告等,应用于首页
    void getResource(List<TbCategory> categoryList, Model model);
    //根据caId得到对应资源类，不分页
    List<TbResource> selectListByCaId(Integer caId);
    //得到考试时间和联系方式,追加底部链接信息
    void getTimeInfor(Model model) throws ParseException;
    
    //判断标题是否重复/是否置顶，有的话把其他都设成否（取消置顶）sign==1代表判断标题是否重复，sign==0代表指定操作 
    //返回值1代表标题是否重复，2代表是否置顶，0代表二者都没
    int getNumber(TbResource record,Model model,int sign);
    
    //返回系统日期
    public String Date();
    
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
    //对于轮播图的分页查看那种不需要PID和ID的
    public PageBean<TbResource>  findingByPage(int currentPage,String inquiry,int pageSize);
    //根据id来删除资源类
    int deleteByPrimaryKey(Integer id);
    //插入资源类
    int insert(TbResource record);
	//根据reTitle来查询
    public TbResource selectByReTitle(String reTitle);//张金高用
    //根据reContent来查询
    public TbResource selectByReContent(String reContent);
    //上传图片
    public String uploadPicture(MultipartFile picture, String path) throws Exception;
    //上传文件
    public String uploadFile(MultipartFile file, String path) throws Exception;
    //文件的下载
    public void down(HttpServletRequest request,HttpServletResponse response,int id) throws Exception;
    //这种删除/修改类别类中的信息，resource表跟着删除/修改，id只是为了区分是修改还是删除，修改是1、删除是2,3是验证图片或文件是否重复,
    void updateDeleteTbResourceByCaId(TbResource record,int id);
}