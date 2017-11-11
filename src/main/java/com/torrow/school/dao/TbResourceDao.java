package com.torrow.school.dao;
import org.apache.ibatis.annotations.Param;
import com.torrow.school.entity.TbResource;

/**
 * @author 安李杰
 *
 * @2017年10月15日下午4:21:03
 */
public interface TbResourceDao {
	
	/**
	 * @param caId
	 * @return 根据caId来删除
	 */
	public int deleteByCaId(@Param("caId")Integer caId);

	public TbResource selectByCaId(@Param("caId")Integer caId);
	//根据reTitle来查询
	public TbResource selectByTitle(@Param("reTitle")String reTitle);

	public TbResource selectByContent(@Param("reContent")String reContent);
	
}