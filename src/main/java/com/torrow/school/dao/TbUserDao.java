package com.torrow.school.dao;

import com.torrow.school.entity.TbUser;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface TbUserDao {
	
    int deleteByPrimaryKey(Integer usId);

    int insert(TbUser record);	//添加用户

    TbUser selectByPrimaryKey(Integer usId);

    List<TbUser> selectAll();

    int updateByPrimaryKey(TbUser record);

	TbUser findUserByNameAndPwd(@Param("usEmail")String usEmail, @Param("usPassword")String usPassword);
}