
package com.torrow.school.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.torrow.school.base.BaseDao;
import com.torrow.school.entity.TbResource;
import com.torrow.school.service.TbResourceService;
import com.torrow.school.util.PageBean;

/**
 * @author 安李杰
 *
 * @2017年10月10日上午9:07:28
 */
@Service
public class TbResourceServiceImpl extends BaseDao<TbResource> implements TbResourceService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int deleteByPrimaryKey(Integer reId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TbResource record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TbResource selectByPrimaryKey(Integer reId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbResource> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(TbResource record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageBean<TbResource> findPage(int currentPage) {
		return this.pageCut(currentPage);
	}

}
