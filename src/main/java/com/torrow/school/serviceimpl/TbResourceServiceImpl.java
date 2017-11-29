
package com.torrow.school.serviceimpl;

import java.util.ArrayList;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
		List<TbCategory> tbCategory = tbCategoryDao.selectAllCaId();
		String name=record.getCaName();
		List<TbResource> tbResource = tbResourceDao.queryAll(name);
		for (TbCategory item : tbCategory) {
			if (item.getCaPid()!=null) {
				if (item.getCaPid()==record.getCaPid()) {
					for (TbResource it : tbResource) { 
						if (it.getCaId()==item.getCaId()) {
							list.add(it);
						}
					}
				} else {
					for (TbResource it : tbResource) {
						if (it.getCaId()==item.getCaId() && it.getCaId()==record.getCaId()) {
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

	@Override
	public void getResource(List<TbCategory> categoryList, Model model) {
		List<Integer> caIdNews = new ArrayList<Integer>();//学校新闻类别类的id
		List<Integer> caIdGenerals = new ArrayList<Integer>();//学校概括类别类的id
		int noticeCaId = 0;//通知公告类别类id
		int resourcesId = 0;//资源下载类别类id
		int pId = 0;
		for(int i=0;i<categoryList.size();i++){//由父id得到类别类id
			pId = categoryList.get(i).getCaPid();
			if(pId==2) {//校园新闻
				caIdNews.add(categoryList.get(i).getCaId()); 
			} else if(pId==6) {//通知公告
				noticeCaId = categoryList.get(i).getCaId();
			} else if(pId==11) {//资源下载
				resourcesId = categoryList.get(i).getCaId();
			} else if(pId==1) {//学校概括
				caIdGenerals.add(categoryList.get(i).getCaId());
			} 
		}
		TbResource general = null;
		for(int i=0;i<caIdGenerals.size();i++){//将首页的展示学校简介等封装进model，此处定死概括类四个名称
			general = this.selectByCaId(caIdGenerals.get(i));
			if(general!=null){
				String cutPicture = this.getPicture(general.getReContent(), 1);//剪取图片前部分
				if(cutPicture!=null){
					general.setReContent(cutPicture);//剪取图片前部分				
				}		
				if(general.getCaName().equals("学校简介")){
					model.addAttribute("schoolGeneralIndex", general);
				} else if(general.getCaName().equals("建校历史")){
					model.addAttribute("schoolHistory", general);
				} else if(general.getCaName().equals("学校荣誉")){
					model.addAttribute("schoolHonorIndex", general);
				} else if(general.getCaName().equals("教学成果")){
					model.addAttribute("educationAchieveIndex", general);
				}
			}
		}
		getResources(caIdNews,caIdGenerals,noticeCaId,resourcesId,model);//将全部资源类按照条数要求，倒序要求得到
	}
	
	//将全部资源类按照条数要求，倒序要求得到
	public void getResources(List<Integer> caIdNews, List<Integer> caIdGenerals, int noticeCaId, int resourcesId,Model model){
		List<TbResource> sNews = new ArrayList<TbResource>();//学校新闻，按倒序得到，只要8条,没有图片不要
		List<TbResource> sNotices = new ArrayList<TbResource>();//学校公告，按倒序得到，只要8条
		List<TbResource> resourcesDown = new ArrayList<TbResource>();//资源下载，按倒序得到，只要8条
		List<TbResource> allResources = this.selectAll();
		for(int i=allResources.size()-1;i>=0;i--){	//倒序得到资源类
			for(int j=0;j<caIdNews.size();j++){
				if(sNews.size()<8&allResources.get(i).getCaId()==caIdNews.get(j)){//当资源类属于新闻时取出
					String content = this.getPicture(allResources.get(i).getReContent(),0);//将图片从富文本中得到并暂存于资源类内容中,没有图片的新闻不要
					if(content!=null){
						allResources.get(i).setReContent(content);
						sNews.add(allResources.get(i));
					}
				}
			}
			if(sNotices.size()<8&&allResources.get(i).getCaId()==noticeCaId){
				sNotices.add(allResources.get(i));
			}
			if(resourcesDown.size()<8&&allResources.get(i).getCaId()==resourcesId){
				resourcesDown.add(allResources.get(i));
			}
		}
		model.addAttribute("notices", sNotices);
		model.addAttribute("sNews", sNews);
		model.addAttribute("resourcesDown", resourcesDown);
	}
	
	/**
	 * @param content
	 * @param where 使用处，在学校简介，建校历史首页处只要图片前
	 * @return 	
	 * 用于从富文本中分离图片，此方法仅存在于该类中，接口层没有相应方法
	 */
	public String getPicture(String content,int where){	
		String imgInternet = "<img alt=\"\" src=\"";// \"\"是为了加入"" alt在前
		String imglocal = "<img src=\"";//src在前
		int startIndex = -1;
		int stopIndex = -1;
		int length = 0;//src内容前面的部分
		String contentTemp="";//用于存放从富文本中得到的图片
		if(content.indexOf(imgInternet)!=-1){
			startIndex = content.indexOf(imgInternet);
			length = imgInternet.length();
			stopIndex = content.indexOf("\">",startIndex);
		} else if(content.indexOf(imglocal)!=-1) {
			startIndex = content.indexOf(imglocal);
			length = imglocal.length();
			stopIndex = content.indexOf("\" alt=",startIndex);
		}
		if(startIndex!=-1&&where==0){
			for(int i=startIndex+length;i<stopIndex;i++){
				contentTemp +=  content.charAt(i);
			}
			if(contentTemp.indexOf("middleschool")!=-1){
				contentTemp = ".."+contentTemp;//本地图片回到项目名前
			}
		} else if(startIndex!=-1&&where==1){//当有图片是首页学校概括时
			contentTemp = content.split("<img")[0];//截取图片前的部分
		} else {//当无图片是学校概括时
			contentTemp = null;
		}
		return contentTemp;
	}

	@Override
	public TbResource selectByReTitle(String reTitle) {
		
		return tbResourceDao.selectByTitle(reTitle);
	}

	@Override
	public TbResource selectByReContent(String reContent) {
		
		return tbResourceDao.selectByContent(reContent);
	}
	
	@Override
	public String uploadPicture(MultipartFile picture, String path) throws Exception {
		return this.uploadP(picture, path);
	}
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws Exception {
		return this.uploadF(file, path);
	}

	@Override
	public void down(HttpServletRequest request, HttpServletResponse response, int id) throws Exception {
		this.download(request,response,id);
	}

	@Override
	public List<TbResource> selectListByCaId(Integer caId) {
		return tbResourceDao.selectListByCaId(caId);
	}

	@Override
	public void updateDeleteTbResourceByCaId(TbResource record, int id) {
		List<TbResource> tb = this.selectAll();
		if(!tb.isEmpty()) {
			for(TbResource item:tb) {
				if(item.getCaId()==record.getCaId()) {
					if(id==1) {
						item.setCaName(record.getCaName());
						this.updateByPrimaryKey(item);
					}
					if(id==2) {
						this.deleteByPrimaryKey(item.getReId());
					}
				}
			}
		}
		
	}
}
