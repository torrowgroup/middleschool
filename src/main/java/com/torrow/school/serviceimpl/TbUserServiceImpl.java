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
		PageBean<TbUser> pageBean = new PageBean<TbUser>();
		// 每页显示的数据
		int pageSize = 2;
		// 封装总记录数
		int totalCount = this.selectCount();
		// 封装总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		map.put("start", (currentPage - 1) * pageSize);
		map.put("size", pageSize);
		// 封装每页显示的数据
		List<TbUser> lists = tbUserDao.findByPage(map);
		pageBean.setCurrPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setLists(lists);
		pageBean.setTotalPage(num.intValue());
		pageBean.setTotalCount(totalCount);
		log.info(pageBean);
		return pageBean;
	}


	@Override
	public int selectCount() {
		return tbUserDao.selectCount();
	}

}
