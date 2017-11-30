package com.torrow.school.serviceimpl;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbUserDao;
import com.torrow.school.entity.TbCategory;
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
	public PageBean<TbUser> findPage(int currentPage,int pageSize) {
		return this.pageCut(currentPage,pageSize);
	}

	@Override
	public PageBean<TbUser> findPageSplit(List<TbCategory> categoryList,int currentPage,String content, int pageSize) {
		List<TbUser> userList = new ArrayList<TbUser>();//全部按照身份分开的用户
		for(int i=0;i<categoryList.size();i++){
			if(categoryList.get(i).getCaId()!=0){ //非空判断
				userList.addAll(tbUserDao.selectListByCaId(categoryList.get(i).getCaId(),content));				
			}
		}
		int totalCount = userList.size();// 得到总记录数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		List<TbUser> lists = new ArrayList<TbUser>();// 这个集合是为了分页显示的条数
		for (int j = (currentPage - 1) * pageSize; j < currentPage * pageSize && j < totalCount; j++) {
			lists.add(userList.get(j));
		}
		PageBean<TbUser> pageBean = new PageBean<TbUser>(currentPage, pageSize, lists, num.intValue(),totalCount);
		log.info("----"+content+" "+totalCount+" "+pageBean);
		return pageBean;	
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

	@Override
	public int addUser(TbUser tbUser) {
		return this.insertEntity(tbUser);
	}

	@Override
	public int deleteByCaId(Integer caId) {
		return tbUserDao.deleteByCaId(caId);
	}

	@Override
	public int updateByPrimaryKey(TbUser record) {
		return this.updateEntity(record);
	}

	@Override
	public String uploadPicture(MultipartFile picture, String path) throws Exception {
		return this.uploadP(picture, path);
	}
	
	

	@Override
	public TbUser selectByEmail(String email) {
		return tbUserDao.selectByEmail(email);
	}

//	@Override
//	public List<TbUser> selectListByCaId(Integer caId) {
//		return tbUserDao.selectListByCaId(caId);
//	}

	@Override
	public void updateDeleteUserByCaId(TbUser tbUser,int id) {
		List<TbUser> user = this.selectAll();
		if(!user.isEmpty()) {
			for(TbUser item:user) {
				if(item.getCaId()==tbUser.getCaId()) {
					if(id==1) {
						item.setCaName(tbUser.getCaName());
						this.updateByPrimaryKey(item);
					}else if(id==2) {
						this.deleteById(tbUser.getCaId());
					}
				}
			}
		}
		
	}

}
