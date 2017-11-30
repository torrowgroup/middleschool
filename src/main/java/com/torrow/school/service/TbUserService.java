package com.torrow.school.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PageBean;

/**
 * 
 * @author 张金高
 *
 *         2017年10月6日下午4:37:24
 */
public interface TbUserService {
	// 得到分页
	PageBean<TbUser> findPage(int currentPage,int pageSize);
	// 得到所有的用户
	List<TbUser> selectAll();
	// 根据id得到用户
	TbUser selectById(int id);
	// 根据id删除用户
	int deleteById(int id);
	// 添加用户
	int addUser(TbUser tbUser);
	// 修改用户
	int updateByPrimaryKey(TbUser record);
	//按邮件得到用户
	TbUser selectByEmail(String email);
	// 上传图片
	String uploadPicture(MultipartFile picture, String path) throws Exception;
	// 这个方法是为了根据caId来查找的
//	TbUser selectByCaId(Integer caId);// 安李杰
	// 这个方法根据caId来删除
	int deleteByCaId(Integer caId);
	// 登陆的方法
	TbUser login(String usEmail, String usPassword);
	//按照身份分开所有用户
	PageBean<TbUser> findPageSplit(List<TbCategory> categoryList,int currentPage, String content, int pageSize);
	//这种删除/修改类别类中的信息，user表跟着删除/修改，id只是为了区分是修改还是删除，修改是1、删除是2
	void updateDeleteUserByCaId(TbUser tbUser,int id);
	
}