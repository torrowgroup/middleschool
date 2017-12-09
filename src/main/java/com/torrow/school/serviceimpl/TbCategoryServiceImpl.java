package com.torrow.school.serviceimpl;
import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
		return this.updateEntity(record);
	}
	
	@Override
	public TbCategory selectCaName(String caName) {
		return tbCategoryDao.findCategoryByCaName(caName);
	}

	@Override
	public PageBean<TbCategory> findPage(int currentPage,int pageSize) {
		return this.pageCut(currentPage,pageSize);
	}

	@Override
	public List<TbCategory> selectByPid(List<Integer> pidList) {
		List<TbCategory> categoryList = this.selectAllEntity();
		int temp; //将变量放在循环外，节省空间
		for(int i=0;i<categoryList.size();i++){	//得到所有符合用户部分的类别类
			temp = 0;
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
	
	@Override
	public void getCategory(Integer rId,Model model){
		List<TbCategory> category = this.selectAll();
		List<TbCategory> generals = new ArrayList<TbCategory>();//概括类
		List<TbCategory> schoolNews = new ArrayList<TbCategory>();//校园新闻包括上传类里面的学生管理和教师管理
		List<TbCategory> educations = new ArrayList<TbCategory>();//教育教研处 教研组
		List<TbCategory> literature = new ArrayList<TbCategory>();//校园文学
		for(int i=0;i<category.size();i++){
			if(rId!=null&&rId!=0&&category.get(i).getCaId()==rId){	//得到用户使用的类别类，并保存在model中
				log.info("useCategory:"+category.get(i));
				model.addAttribute("useCategory", category.get(i));
			}
			if(category.get(i).getCaPid()==1||category.get(i).getCaPid()==7){
				generals.add(category.get(i));
			} else if(category.get(i).getCaPid()==2||category.get(i).getCaPid()==9) {
				schoolNews.add(category.get(i));
			} else if(category.get(i).getCaPid()==3){
				educations.add(category.get(i));
			} else if(category.get(i).getCaPid()==12){
				literature.add(category.get(i));
			}
		}
		model.addAttribute("generals", generals);
		model.addAttribute("schoolNews", schoolNews);
		model.addAttribute("educations", educations);
		model.addAttribute("literature", literature);
	}

	@Override
	public void addBySelectPid(Model model,int Pid) {
		List<TbCategory> list = this.queryByPid(Pid);
		if (!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		} else {
			model.addAttribute("sign", 1);
		}
	}

	@Override
	public void findAllCategory(Model model) {
		int Pid=1;//概括类的
		List<TbCategory> list=this.queryByPid(Pid);
		int id=7;//校园风光
		List<TbCategory> item=this.queryByPid(id);
		int Pd = 2;//新闻类
		List<TbCategory> news = this.queryByPid(Pd);
		int d=9;//学生管理、教师上传
		List<TbCategory> upload = this.queryByPid(d);
		int i=3;
		List<TbCategory> education = this.queryByPid(i);
		int j=12;
		List<TbCategory> literature = this.queryByPid(j);
		int f=11;
		List<TbCategory> download = this.queryByPid(f);
		model.addAttribute("downloadList", download);
		model.addAttribute("literatureList",literature);
		model.addAttribute("educationList",education);
		model.addAttribute("uploadList",upload);
		model.addAttribute("newsList", news);
		model.addAttribute("generalList", list);
		model.addAttribute("sceneryList", item);	
	}

	@Override
	public PageBean<TbCategory> findingByPaging(int currentPage, String inquiry, int pageSize) {
		List<TbCategory> message = tbCategoryDao.selectBlur(inquiry);
		int totalCount = message.size();
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		List<TbCategory> messageList = new ArrayList<TbCategory>();// 这个集合是为了分页显示的条数
		for (int j = (currentPage - 1) * pageSize; j < currentPage * pageSize && j < totalCount; j++) {
			messageList.add(message.get(j));
		}
		PageBean<TbCategory> pageBean = new PageBean<TbCategory>(currentPage, pageSize, messageList, num.intValue(), totalCount);
		return pageBean;
	}

	
}
