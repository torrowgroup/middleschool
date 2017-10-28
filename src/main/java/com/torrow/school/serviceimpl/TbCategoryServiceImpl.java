package com.torrow.school.serviceimpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbCategoryDao;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.service.TbCategoryService;
import com.torrow.school.util.PageBean;
@Service
public class TbCategoryServiceImpl extends BaseDao<TbCategory> implements TbCategoryService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private TbCategoryDao tbCategoryDao;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return this.deleteEntity(id);
	}

	@Override
	public void insert(TbCategory record) {
        this.insertEntity(record);
	}

	@Override
	public TbCategory selectByPrimaryKey(Integer id) {
		return this.selectOneEntity(id);
	}

	//得到所有类别类，调用basedao里方法
	@Override
	public List<TbCategory> selectAll() {
		return this.selectAllEntity();
	}

	@Override
	public int updateByPrimaryKey(TbCategory record) {
		log.info(record+"7744");
		return this.updateEntity(record);
	}
	
	@Override
	public TbCategory selectCaName(String caName) {
		return tbCategoryDao.findCategoryByCaName(caName);
	}

	@Override
	public PageBean<TbCategory> findPage(int currentPage) {
		return this.pageCut(currentPage);
	}

	@Override
	public List<TbCategory> selectByPid(List<Integer> pidList) {
		List<TbCategory> categoryList = this.selectAllEntity();
		int temp = 0; //将变量放在循环外，节省空间
		for(int i=0;i<categoryList.size();i++){	//得到所有符合用户部分的类别类
			for(int j=0;j<pidList.size();j++){
				if(categoryList.get(i).getCaPid()==pidList.get(j)){
					temp=1;
					break;
				}
			}
			if(temp==0){
				categoryList.remove(i);
				i--;
			}
		}
		return categoryList;
	}

	@Override
	public List<TbCategory> queryByPid(int Pid) {
		List<TbCategory> categoryList = new ArrayList<TbCategory>();
		List<TbCategory> list=this.selectAllEntity();
		for(TbCategory item:list) {
			if(item.getCaPid()==Pid) {
				categoryList.add(item);
			}
		}
		return categoryList;
	}
	
}
