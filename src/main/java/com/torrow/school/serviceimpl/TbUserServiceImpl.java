package com.torrow.school.serviceimpl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbUserDao;
import com.torrow.school.entity.TbUser;
import com.torrow.school.service.TbUserService;
import com.torrow.school.util.Page;
import com.torrow.school.util.PageBean;
import com.torrow.school.util.PagingVO;

/*
 * 用户业务层，继承基础类，实现业务类
 */
@Service
public class TbUserServiceImpl extends BaseDao<TbUser> implements TbUserService{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private TbUserDao tbUserDao;
	
	@Override
	public int deleteByPrimaryKey(Integer usId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TbUser record) {		//添加用户
		return this.insertEntity(record);
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
	
	@Override
	public TbUser login(String usEmail, String usPassword) {
		return tbUserDao.findUserByNameAndPwd(usEmail,usPassword);
	}

	@Override  
	public void showUsersByPage(HttpServletRequest request, Model model) {  
	    String pageNow = request.getParameter("pageNow");  
	    System.out.println("777");
	    Page page = null;  
	  
	    List<TbUser> users = new ArrayList<TbUser>();  
	  
	    int totalCount = (int) tbUserDao.getUsersCount();  
	  
	    List<TbUser> allProducts;
		if (pageNow != null) {  
	        page = new Page(totalCount, Integer.parseInt(pageNow));  
	        allProducts = this.tbUserDao.selectUsersByPage(page.getStartPos(), page.getPageSize());  
	    } else {  
	        page = new Page(totalCount, 1);  
	        allProducts = this.tbUserDao.selectUsersByPage(page.getStartPos(), page.getPageSize());  
	    }  
	  
	    model.addAttribute("users", users);  
	    model.addAttribute("page", page);  
	}  

	 @Override
	    public int selectCount() {
	        return tbUserDao.selectCount();
	    }

	    @Override
	    public PageBean<TbUser> findByPage(int currentPage) {
	        HashMap<String,Object> map = new HashMap<String,Object>();
	        PageBean<TbUser> pageBean = new PageBean<TbUser>();

	        //封装当前页数
	        pageBean.setCurrPage(currentPage);

	        //每页显示的数据
	        int pageSize=2;
	        pageBean.setPageSize(pageSize);

	        //封装总记录数
	        int totalCount = tbUserDao.selectCount();
	        pageBean.setTotalCount(totalCount);

	        //封装总页数
	        double tc = totalCount;
	        Double num =Math.ceil(tc/pageSize);//向上取整
	        pageBean.setTotalPage(num.intValue());

	        map.put("start",(currentPage-1)*pageSize);
	        map.put("size", pageBean.getPageSize());
	        //封装每页显示的数据
	        List<TbUser> lists = tbUserDao.findByPage(map);
	        pageBean.setLists(lists);

	        return pageBean;
	    }

		@Override
		public List<TbUser> findByPaging(Integer toPageNo) throws Exception {
			PagingVO pagingVO = new PagingVO();
	        pagingVO.setToPageNo(toPageNo);

	        List<TbUser> list = tbUserDao.findByPaging(pagingVO);

	        return list;
		}

		

	
}
