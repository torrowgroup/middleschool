
package com.torrow.school.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.torrow.school.base.BaseDao;
import com.torrow.school.entity.TbMessage;
import com.torrow.school.service.TbMessageService;
import com.torrow.school.util.PageBean;

@Service
public class TbMessageServiceImpl extends BaseDao<TbMessage> implements TbMessageService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int deleteByPrimaryKey(Integer meId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TbMessage record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TbMessage selectByPrimaryKey(Integer meId) {
		return this.selectByPrimaryKey(meId);
	}

	@Override
	public List<TbMessage> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(TbMessage record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageBean<TbMessage> findPage(int currentPage) {
		return this.pageCut(currentPage);
	}
	

}
