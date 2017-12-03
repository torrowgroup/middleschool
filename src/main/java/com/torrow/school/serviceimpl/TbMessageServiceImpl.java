
package com.torrow.school.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbMessageDao;
import com.torrow.school.entity.TbMessage;
import com.torrow.school.service.TbMessageService;
import com.torrow.school.util.PageBean;

@Service
public class TbMessageServiceImpl extends BaseDao<TbMessage> implements TbMessageService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	TbMessageDao messageDao;
	
	@Override
	public int deleteByPrimaryKey(Integer meId) {
		return this.deleteEntity(meId);
	}

	@Override
	public int insert(TbMessage record) {
		return this.insertEntity(record);
	}

	@Override
	public TbMessage selectByPrimaryKey(Integer meId) {
		return this.selectOneEntity(meId);
	}

	@Override
	public PageBean<TbMessage> findPage(int currentPage,String inquiry,int pageSize) {
		List<TbMessage> message = messageDao.selectBlur(inquiry);
		int totalCount = message.size();
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		List<TbMessage> messageList = new ArrayList<TbMessage>();// 这个集合是为了分页显示的条数
		for (int j = (currentPage - 1) * pageSize; j < currentPage * pageSize && j < totalCount; j++) {
			messageList.add(message.get(j));
		}
		PageBean<TbMessage> pageBean = new PageBean<TbMessage>(currentPage, pageSize, messageList, num.intValue(), totalCount);
		return pageBean;
	}

	@Override
	public int reply(int id, String meReply) {
		TbMessage message =  this.selectOneEntity(id);
		message.setMeReply(meReply);
		message.setMeStatus("已回复");
		return this.updateEntity(message);
	}

	@Override
	public String uploadImg(MultipartFile file,String path) throws Exception {
		return this.uploadP(file,path);
	}

	@Override
	public int updateByPrimaryKey(TbMessage record) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
