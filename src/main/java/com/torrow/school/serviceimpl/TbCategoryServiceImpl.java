
package com.torrow.school.serviceimpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbCategoryDao;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.service.TbCategoryService;
@Service
public class TbCategoryServiceImpl extends BaseDao<TbCategory> implements TbCategoryService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private TbCategoryDao tbCategoryDao;
	
	@Override
	public int deleteByPrimaryKey(Integer caId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insert(TbCategory record) {
        this.insertEntity(record);
	}

	@Override
	public TbCategory selectByPrimaryKey(Integer caId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbCategory> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(TbCategory record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TbCategory selectCaName(String caName) {
		return tbCategoryDao.findCategoryByCaName(caName);
	}

	@Override
	public TbCategory findPage(int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
