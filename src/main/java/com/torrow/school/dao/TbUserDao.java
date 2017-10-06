package com.torrow.school.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PagingVO;

public interface TbUserDao {
	
	//登陆的方法
	TbUser findUserByNameAndPwd(@Param("usEmail")String usEmail, @Param("usPassword")String usPassword);
    
    /** 
     *  
     * @param page 
     * @return startPos},#{pageSize}  
     */  
    public List<TbUser> selectUsersByPage(@Param(value="startPos") Integer startPos,@Param(value="pageSize") Integer pageSize);  
      
    /** 
     * 
     * @return 
     */  
    public long getUsersCount();
		
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
    
	List<TbUser> findByPaging(PagingVO pagingVO);
	
	//查询所有
	public List<TbUser> selectAll();
}