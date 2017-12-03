package com.torrow.school.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.torrow.school.entity.TbMessage;

public interface TbMessageDao {
	
	/**
	 * @param inquiry
	 * @return 倒序模糊查找所有留言
	 */
	List<TbMessage> selectBlur(@Param("inquiry") String inquiry);

}