package com.torrow.school.service;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PageBean;
/**
 * 
 * @author 张金高
 *
 * 2017年10月6日下午4:37:24
 */
public interface TbUserService {

    //登陆的方法
	TbUser login(String usEmail, String usPassword);

	//得到分页
    PageBean<TbUser> findPage(int currentPage);
}