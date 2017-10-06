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
	public PageBean<TbUser> findByPage(int currentPage) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		PageBean<TbUser> pageBean = new PageBean<TbUser>();
		// 封装当前页数
		pageBean.setCurrPage(currentPage);
		// 每页显示的数据
		int pageSize = 2;
		pageBean.setPageSize(pageSize);
		// 封装总记录数
		int totalCount = tbUserDao.selectCount();
		pageBean.setTotalCount(totalCount);
		// 封装总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		pageBean.setTotalPage(num.intValue());
		map.put("start", (currentPage - 1) * pageSize);
		map.put("size", pageBean.getPageSize());
		// 封装每页显示的数据
		List<TbUser> lists = tbUserDao.findByPage(map);
		pageBean.setLists(lists);
		log.info(pageBean);
		return pageBean;
	}


}
