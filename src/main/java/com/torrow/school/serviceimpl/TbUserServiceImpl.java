
package com.torrow.school.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbUserDao;
import com.torrow.school.entity.TbUser;
import com.torrow.school.service.TbUserService;
/*
 * 用户业务层，继承基础类，实现业务类
 */
@Service
public class TbUserServiceImpl extends BaseDao<TbUser> implements TbUserService{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private TbUserDao tbUserDao;
	
	@Override
	public int deleteByPrimaryKey(Integer usId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TbUser record) {		//添加用户
		return this.insertEntity(record);
	}

	@Override
	public TbUser selectByPrimaryKey(Integer usId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbUser> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(TbUser record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.torrow.school.service.TbUserService#login(java.lang.String, java.lang.String)
	 * 登录的方法
	 */	
	@Override
	public TbUser login(String usEmail, String usPassword) {
		
		return tbUserDao.findUserByNameAndPwd(usEmail,usPassword); 
	}

	
}
