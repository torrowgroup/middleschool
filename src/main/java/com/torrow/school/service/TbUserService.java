package com.torrow.school.service;
import java.util.List;

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
    //得到所有的用户
	List<TbUser> selectAll();
	//根据id得到用户
	TbUser selectById(int id);
	//根据id删除用户
	int deleteById(int id);
}