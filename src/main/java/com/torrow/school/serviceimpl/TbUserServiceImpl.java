package com.torrow.school.serviceimpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
	public PageBean<TbUser> findPage(int currentPage,int pageSize) {
		return this.pageCut(currentPage,pageSize);		
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
	public TbUser selectByCaId(Integer caId) {
		return tbUserDao.selectByCaId(caId);
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

}
