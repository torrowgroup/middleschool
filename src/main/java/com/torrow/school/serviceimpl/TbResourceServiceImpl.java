
package com.torrow.school.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.torrow.school.base.BaseDao;
import com.torrow.school.dao.TbCategoryDao;
import com.torrow.school.dao.TbResourceDao;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.service.TbResourceService;
import com.torrow.school.util.PageBean;

/**
 * @author 安李杰
 *
 * @2017年10月10日上午9:07:28
 */
@Service
public class TbResourceServiceImpl extends BaseDao<TbResource> implements TbResourceService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private TbCategoryDao tbCategoryDao;

	@Resource
	private TbResourceDao tbResourceDao;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return this.deleteEntity(id);
	}

	@Override
	public int insert(TbResource record) {
		return this.insertEntity(record);
	}

	@Override
	public TbResource selectByPrimaryKey(Integer id) {
		return this.selectOneEntity(id);
	}

	@Override
	public List<TbResource> selectAll() {
		return this.selectAllEntity();
	}

	@Override
	public int updateByPrimaryKey(TbResource record) {
		return this.updateEntity(record);
	}

	@Override
	public PageBean<TbResource> findPage(int currentPage,int pageSize) {
		return this.pageCut(currentPage,pageSize);
	}

	@Override
	public PageBean<TbResource> findingByPaging(int currentPage, TbCategory record,int pageSize) {
		List<TbResource> list = new ArrayList<TbResource>();// 这个集合是为了把得到资源类与类别类caId相同的的数据
		/*int pageSize = 4;*/
		List<TbCategory> tbCategory = tbCategoryDao.selectAllCaId();
		List<TbResource> tbResource = this.selectAll();
		for (TbCategory item : tbCategory) {
			if (!item.getCaPid().equals(null)) {
				if (item.getCaPid().equals(record.getCaPid())) {
					for (TbResource it : tbResource) {
						if (it.getCaId().equals(item.getCaId())) {
							list.add(it);
						}
					}
				} else {
					for (TbResource it : tbResource) {
						if (it.getCaId().equals(item.getCaId()) && it.getCaId().equals(record.getCaId())) {
							list.add(it);
						}
					}
				}
			}
		}
		int totalCount = list.size();// 得到总记录数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		List<TbResource> lists = new ArrayList<TbResource>();// 这个集合是为了分页显示的条数
		for (int j = (currentPage - 1) * pageSize; j < currentPage * pageSize && j < totalCount; j++) {
			lists.add(list.get(j));
		}
		PageBean<TbResource> pageBean = new PageBean<TbResource>(currentPage, pageSize, lists, num.intValue(),
				totalCount);
		return pageBean;
	}

	@Override
	public int deleteByCaId(Integer caId) {
		return tbResourceDao.deleteByCaId(caId);
	}

	@Override
	public TbResource selectByCaId(Integer caId) {
		return tbResourceDao.selectByCaId(caId);
	}

	@Override
	public TbResource selectOne(Integer gId) {
		return this.selectByCaId(gId);
	}

}
