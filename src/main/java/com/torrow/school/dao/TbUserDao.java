package com.torrow.school.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import com.torrow.school.entity.TbUser;

public interface TbUserDao {
	//登陆的方法
	TbUser findUserByNameAndPwd(@Param("usEmail")String usEmail, @Param("usPassword")String usPassword);
   	
    /**
     * 查询用户记录总数
     * @return
     */
    int selectCount();
    /**
     * 分页操作，调用findByPage limit分页方法
     * @param map
     * @return
     */
    List<TbUser> findByPage(HashMap<String,Object> map);
    
	//查询所有
	public List<TbUser> selectAll();
	
	//根据caId来查询
	public TbUser selectByCaId(@Param("caId")Integer caId);
	//根据caId来删除
	public int deleteByCaId(@Param("caId")Integer caId);
	//张金高
	//根绝邮箱查询用户
	TbUser selectByEmail(String email);
	//根据caId模糊得到list集合用户
	List<TbUser> selectListByCaId(@Param("caId")Integer caId,@Param("content")String content);
	
}