package com.torrow.school.service;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PageBean;

public interface TbUserService {

    //登陆的方法
	public TbUser login(String usEmail, String usPassword);
	
	//进行分页的操作
    PageBean<TbUser> findByPage(int currentPage);



}