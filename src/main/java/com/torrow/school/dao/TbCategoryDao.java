package com.torrow.school.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.torrow.school.entity.TbCategory;

public interface TbCategoryDao {
	

	//查看类别名称是否重复
	public TbCategory findCategoryByCaName(@Param("caName")String caName);//张金高用于根据类别类名称获得类别类

	public List<TbCategory> selectAllCaId();

	public  List<TbCategory> selectBlur(@Param("inquiry")String inquiry);
		

	

}