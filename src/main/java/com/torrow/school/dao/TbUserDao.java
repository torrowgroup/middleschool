package com.torrow.school.dao;

import com.torrow.school.entity.TbUser;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface TbUserDao {
	
	//登陆的方法
	TbUser findUserByNameAndPwd(@Param("usEmail")String usEmail, @Param("usPassword")String usPassword);
}