
package com.torrow.school.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.torrow.school.dao.TbUserDao;
import com.torrow.school.entity.TbUser;
import com.torrow.school.service.TbUserService;

@Service
public class TbUserServiceImpl implements TbUserService{ 
	
	@Resource
	private TbUserDao tbUserDao;
	
	@Override
	public int deleteByPrimaryKey(Integer usId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TbUser record) {		//添加用户
		System.out.println("---");
		return tbUserDao.insert(record);
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

	
}
