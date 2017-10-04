package com.torrow.school.dao;

import com.torrow.school.entity.TbUser;

public interface TbUserDao {
	
	//登陆的方法
	public TbUser findUserByNameAndPwd(String usEmail,String usPassword);
	
	public TbUser selectByPrimaryKey(int id);
}