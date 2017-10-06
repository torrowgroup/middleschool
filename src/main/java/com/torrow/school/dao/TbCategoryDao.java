package com.torrow.school.dao;

import org.apache.ibatis.annotations.Param;

import com.torrow.school.entity.TbCategory;

public interface TbCategoryDao {
	

	//查看类别名称是否重复
	public TbCategory findCategoryByCaName(@Param("caName")String caName);


}