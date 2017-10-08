package com.torrow.school.serviceimpl;
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
		return this.pageCut(currentPage);		
	}

	@Override
	public List<TbUser> selectAll() {
		return this.selectAllEntity();
	}

	@Override
	public TbUser selectById(int id) {
		return this.selectOneEntity(id);
	}

	@Override
	public int deleteById(int id) {
		return this.deleteEntity(id);
	}

}
