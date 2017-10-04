package com.torrow.school.service;

import com.torrow.school.entity.TbUser;
import java.util.List;


public interface TbUserService {
	
    int deleteByPrimaryKey(Integer usId);

    int insert(TbUser record);

    TbUser selectByPrimaryKey(Integer usId);

    List<TbUser> selectAll();

    int updateByPrimaryKey(TbUser record);
    //登陆的方法
	public TbUser login(String usEmail, String usPassword);
}