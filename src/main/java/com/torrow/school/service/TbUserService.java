package com.torrow.school.service;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PageBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;


public interface TbUserService {
	
    int deleteByPrimaryKey(Integer usId);

    int insert(TbUser record);

    TbUser selectByPrimaryKey(Integer usId);

    List<TbUser> selectAll();

    int updateByPrimaryKey(TbUser record);
    //登陆的方法
	public TbUser login(String usEmail, String usPassword);
	
	/** 
     * 分页显示商品 
     * @param request 
     * @param model 
     * @param loginUserId 
     */  
    void showUsersByPage(HttpServletRequest request,Model model);
    
    int selectCount();

    PageBean<TbUser> findByPage(int currentPage);

    //获取分页查询学生信息
    List<TbUser> findByPaging(Integer toPageNo) throws Exception;
}