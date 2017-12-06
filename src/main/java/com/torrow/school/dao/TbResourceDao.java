package com.torrow.school.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.torrow.school.entity.TbResource;

/**
 * @author 安李杰
 *
 * @2017年10月15日下午4:21:03
 */
public interface TbResourceDao {
	
	//根据caId得到所有资源类，不分页，张金高加
	List<TbResource> selectListByCaId(@Param("caId")Integer caId);

	public TbResource selectByCaId(@Param("caId")Integer caId);
	//根据reTitle来查询
	public TbResource selectByTitle(@Param("reTitle")String reTitle);

	public TbResource selectByContent(@Param("reContent")String reContent);

	public List<TbResource> queryAll(@Param("name")String name);
	
	public List<TbResource> selectBlur(@Param("inquiry")String inquiry);
	
}