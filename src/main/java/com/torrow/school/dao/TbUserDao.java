package com.torrow.school.dao;

import org.apache.ibatis.annotations.Param;

import com.torrow.school.entity.TbUser;

public interface TbUserDao {
	
	//登陆的方法
	TbUser findUserByNameAndPwd(@Param("usEmail")String usEmail, @Param("usPassword")String usPassword);
	
}