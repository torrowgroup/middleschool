package com.torrow.school.serviceimpl;

import java.util.HashMap;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbUserDao;
import com.torrow.school.entity.TbUser;
import com.torrow.school.service.TbUserService;
import com.torrow.school.util.PageBean;

/*
 * 用户业务层，继承基础类，实现业务类
 */
@Service
public class TbUserServiceImpl extends BaseDao<TbUser> implements TbUserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private TbUserDao tbUserDao;

	@Override
	public TbUser login(String usEmail, String usPassword) {
		return tbUserDao.findUserByNameAndPwd(usEmail, usPassword);
	}

	@Override
	public PageBean<TbUser> findPage(int currentPage) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pageSize = 2;
		int totalCount = tbUserDao.selectCount();
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		map.put("start", (currentPage - 1) * pageSize);
		map.put("size", pageSize);
		List<TbUser> lists = tbUserDao.findByPage(map);
		PageBean<TbUser> pageBean = new PageBean<TbUser>(currentPage,pageSize,lists,num.intValue(),totalCount);		
		return pageBean;
	}

}
