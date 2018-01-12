
package com.torrow.school.serviceimpl;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	public PageBean<TbResource> findPage(int currentPage, int pageSize) {
		return this.pageCut(currentPage, pageSize);
	}

	@Override
	public PageBean<TbResource> findingByPaging(int currentPage, TbCategory record, int pageSize) {
		// 这个集合是为了把得到资源类与类别类caId相同的的数据
		List<TbResource> list = new ArrayList<TbResource>();
		//这个集合才是最终的集合，把取消置顶状态的的放置在最前边
		List<TbResource> tbLists=new ArrayList<TbResource>();
		List<TbCategory> tbCategory = tbCategoryDao.selectAllCaId();
		String name = record.getCaName();
		List<TbResource> tbResource = tbResourceDao.queryAll(name);
		for (TbCategory item : tbCategory) {
			if (item.getCaPid() != null) {
				if (item.getCaPid() == record.getCaPid()) {
					for (int i = tbResource.size() - 1; i >= 0; i--) {
						if (tbResource.get(i).getCaId() == item.getCaId()) {
							if(tbResource.get(i).getSpare()!=null) {
								if(tbResource.get(i).getSpare().equals("是")) {
									tbLists.add(tbResource.get(i));
								}else {
									list.add(tbResource.get(i));
								}
							} else {
								list.add(tbResource.get(i));
							}
						}
					}
				} else {
					for (int i = tbResource.size() - 1; i >= 0; i--) {
						if (tbResource.get(i).getCaId() == item.getCaId()
								&& tbResource.get(i).getCaId() == record.getCaId()) {
							if(tbResource.get(i).getSpare()!=null) {
								if(tbResource.get(i).getSpare().equals("是")) {
									tbLists.add(tbResource.get(i));
								}else {
									list.add(tbResource.get(i));
								}
							} else {
								list.add(tbResource.get(i));
							}
						}
					}
				}
			}
		}
		for(int i=0;i<=list.size()-1;i++) {
			tbLists.add(list.get(i));
		}
		int totalCount = tbLists.size();// 得到总记录数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		List<TbResource> lists = new ArrayList<TbResource>();// 这个集合是为了分页显示的条数
		for (int j = (currentPage - 1) * pageSize; j < currentPage * pageSize && j < totalCount; j++) {
			lists.add(tbLists.get(j));
		}
		PageBean<TbResource> pageBean = new PageBean<TbResource>(currentPage, pageSize, lists, num.intValue(),
				totalCount);
		return pageBean;
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
		List<Integer> caIdNews = new ArrayList<Integer>();// 学校新闻类别类的id
		List<Integer> caIdGenerals = new ArrayList<Integer>();// 学校概括类别类的id
		int noticeCaId = 0;// 通知公告类别类id
		int resourcesId = 0;// 资源下载类别类id
		int pId = 0;
		for (int i = 0; i < categoryList.size(); i++) {// 由父id得到类别类id
			pId = categoryList.get(i).getCaPid();
			if (pId == 2) {// 校园新闻
				caIdNews.add(categoryList.get(i).getCaId());
			} else if (pId == 6) {// 通知公告
				noticeCaId = categoryList.get(i).getCaId();
			} else if (pId == 11) {// 资源下载
				resourcesId = categoryList.get(i).getCaId();
			} else if (pId == 1) {// 学校概括
				caIdGenerals.add(categoryList.get(i).getCaId());
			}
		}
		getResources(caIdNews, caIdGenerals, noticeCaId, resourcesId, model);// 将全部资源类按照条数要求，倒序要求得到
	}

	// 截取内容前30字，不去除html
	public String cutWordWithHtml(String content) {
		if (content.length() > 30) {
			content = content.substring(0, 30);
		}
		content += "......";
		return content;
	}

	// 将全部资源类按照条数要求，倒序要求得到
	public void getResources(List<Integer> caIdNews, List<Integer> caIdGenerals, int noticeCaId, int resourcesId,
			Model model) {
		List<TbResource> sNews = new ArrayList<TbResource>();// 学校新闻，按倒序得到，只要8条,没有图片不要
		List<TbResource> sNotices = new ArrayList<TbResource>();// 学校公告，按倒序得到，只要8条
		List<TbResource> resourcesDown = new ArrayList<TbResource>();// 资源下载，按倒序得到，只要8条
		List<TbResource> generalIndex = new ArrayList<TbResource>();// 学校概括，按顺序得到，只要4条
		List<TbResource> allResources = this.selectAll();
		String reContentTemp ;
		for (int i = 0; i < caIdGenerals.size(); i++) {
			if (generalIndex.size() < 4) {
				TbResource resource = this.selectByCaId(caIdGenerals.get(i));
				if(resource!=null){
					reContentTemp = resource.getReContent(); 
					resource.setReContent(this.cutWordWithHtml(reContentTemp));// 只要30个字符,
					resource.setSpare(this.getPicture(reContentTemp));// 得到图片，暂时存放于备用字段！！！！！
					generalIndex.add(resource);
				}
			}
		}
		for (int i = allResources.size() - 1; i >= 0; i--) { // 倒序得到资源类
			for (int j = 0; j < caIdNews.size(); j++) {
				if (sNews.size() < 8 & allResources.get(i).getCaId() == caIdNews.get(j)) {// 当资源类属于新闻时取出
					String content = this.getPicture(allResources.get(i).getReContent());// 将图片从富文本中得到并暂存于资源类内容中,没有图片的新闻不要
					if (content != null) {
						allResources.get(i).setReContent(content);
						sNews.add(allResources.get(i));
					}
				}
			}
			if (sNotices.size() < 8 && allResources.get(i).getCaId() == noticeCaId) {
				sNotices.add(allResources.get(i));
			}
			if (resourcesDown.size() < 8 && allResources.get(i).getCaId() == resourcesId) {
				resourcesDown.add(allResources.get(i));
			}
		}
		model.addAttribute("generalIndex", generalIndex);// 首页四概括
		model.addAttribute("notices", sNotices);
		model.addAttribute("sNews", sNews);
		model.addAttribute("resourcesDown", resourcesDown);
	}

	/**
	 * @param content
	 * @param where
	 *            使用处，在学校简介，建校历史,校园新闻首页等处只要图片前
	 * @return 用于从富文本中分离图片，此方法仅存在于该类中，接口层没有相应方法
	 */
	public String getPicture(String content) {
		String img = "src=\"";// \"\"是为了加入"" alt在前
		int startIndex = -1;
		int stopIndex = -1;
		int length = 0;// src内容前面的部分
		String contentTemp = "";// 用于存放从富文本中得到的图片
		if (content.indexOf(img) != -1) {
			startIndex = content.indexOf(img);
			length = img.length();
			stopIndex = content.indexOf("\"", startIndex + length);
		}
		if (startIndex != -1) {
			for (int i = startIndex + length; i < stopIndex; i++) {
				contentTemp += content.charAt(i);
			}
			if (contentTemp.indexOf("middleschool") != -1) {
				contentTemp = ".." + contentTemp;// 本地图片回到项目名前
			}
		} else {// 当无图片是学校概括时
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
		this.download(request, response, id);
	}

	@Override
	public List<TbResource> selectListByCaId(Integer caId) {
		return tbResourceDao.selectListByCaId(caId);
	}

	@Override
	public void updateDeleteTbResourceByCaId(TbResource record, int id) {
		List<TbResource> tb = this.selectAll();
		if (!tb.isEmpty()) {
			for (TbResource item : tb) {
				if (item.getCaId() == record.getCaId()) {
					if (id == 1) {
						item.setCaName(record.getCaName());
						this.updateByPrimaryKey(item);
					}
					if (id == 2) {
						this.deleteByPrimaryKey(item.getReId());
					}
				}
			}
		}

	}

	@Override
	public void getTimeInfor(Model model) throws ParseException {
		TbResource midExam = this.selectByReTitle("期中考试");
		TbResource finalExam = this.selectByReTitle("期末考试");
		TbResource catchingExam = this.selectByReTitle("中招考试");
		TbResource phone = this.selectByReTitle("电话");
		TbResource email = this.selectByReTitle("邮箱");
		TbResource address = this.selectByReTitle("地址");
		List<TbResource> resourceList = this.selectAll();
		List<TbResource> linksList = new ArrayList<TbResource>();//底部链接
		List<TbResource> indexScroll = new ArrayList<TbResource>();//首页轮播
		for(int i=0;i<resourceList.size();i++){
			if(resourceList.get(i)!=null&&resourceList.get(i).getCaName().equals("底部链接")){//当属于底部链接时
				linksList.add(resourceList.get(i));
			} else if(resourceList.get(i)!=null&&resourceList.get(i).getReTitle().equals("首页轮播")){//属于前台图片时
				indexScroll.add(resourceList.get(i));
			}
		}
		model.addAttribute("newsPicture", this.selectByReTitle("校园新闻"));
		model.addAttribute("generalPicture", this.selectByReTitle("学校概括"));
		model.addAttribute("educationPicture", this.selectByReTitle("教育教研"));
		model.addAttribute("literaturePicture", this.selectByReTitle("校园文学"));
		model.addAttribute("teacherPicture", this.selectByReTitle("教师风采"));
		model.addAttribute("noticePicture", this.selectByReTitle("公告"));
		model.addAttribute("schoolPicture", this.selectByReTitle("校徽"));
		model.addAttribute("linksList", linksList);
		model.addAttribute("indexScroll",indexScroll);
		model.addAttribute("midExam", this.subtractDay(midExam));
		model.addAttribute("finalExam", this.subtractDay(finalExam));
		model.addAttribute("catchingExam", this.subtractDay(catchingExam));
		model.addAttribute("phone", phone);
		model.addAttribute("email", email);
		model.addAttribute("address", address);
	}

	// 计算离考试所剩时间
	public String subtractDay(TbResource timeResource) throws ParseException {
		if (timeResource != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			Date examTime = df.parse(timeResource.getReContent());
			long beginTime = new Date().getTime();
			long endTime = examTime.getTime();
			if (beginTime <= endTime) {
				return (endTime - beginTime) / (24 * 60 * 60 * 1000) + "";
			}

		}
		return "--";
	}

	// 首页学校简介等处去除html标签 ！！！！不用了
	public String cutWord(String content) {
		int startIndex = -1;
		int stopIndex = -1;
		String contentTemp = "";
		char cutHtml[] = content.toCharArray();
		if (content != null && !content.equals("")) {
			if (content.indexOf("<") != -1) {
				startIndex = content.indexOf("<");
				contentTemp = content.substring(0, startIndex);// 将第一次标签出现前内容放入
				for (int i = startIndex; i < cutHtml.length; i++) {
					if (contentTemp.length() >= 92) {// 显示100个字
						break;
					}
					if (content.indexOf(">", i) != -1) {
						stopIndex = content.indexOf(">", i);
						if (startIndex != -1) {
							i = stopIndex + 1;// 跳过标签内容
						}
						startIndex = -1;
						if (content.indexOf("<", i) > i) {// 当前不是>符时加入
							contentTemp += content.charAt(i);
						} else {
							startIndex = content.indexOf("<", i) + 1;
						}
					} else {
						break;
					}
				}
			} else {
				if (content.length() > 100) {
					contentTemp = content.substring(0, 92);
				} else {
					contentTemp = content;
				}
			}
			String cutEnd = contentTemp.substring(contentTemp.length() - 6, contentTemp.length() - 1);
			if (cutEnd.indexOf('&') != -1) {
				contentTemp = contentTemp.substring(0, contentTemp.length() - 6);
			}
			contentTemp += "......";
		}
		return contentTemp;
	}

	@Override
	public int getNumber(TbResource record, Model model, int sign) {
		List<TbResource> resource = this.selectAll();
		for (TbResource en : resource) {
			if (en.getCaId() == record.getCaId()) {
				// sign==1代表标题是否重复
				if (sign == 1) {
					if (en.getReTitle().equals(record.getReTitle())) {
						return 1;
					}
				} else if (sign == 0) {
					en.setSpare("置顶");
					this.updateByPrimaryKey(en);
					return 2;
				}
			}
		}
		return 0;
	}

	@Override
	public String Date() {
		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH表示24小时制；
		String Date = dFormat.format(date);
		return Date;
	}

	@Override
	public PageBean<TbResource> findingByPage(int currentPage, String inquiry, int pageSize) {
		//这个根据caName来查看
		List<TbResource> message = tbResourceDao.selectBlur(inquiry);
		int totalCount = message.size();
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);// 向上取整
		List<TbResource> messageList = new ArrayList<TbResource>();// 这个集合是为了分页显示的条数
		for (int j = (currentPage - 1) * pageSize; j < currentPage * pageSize && j < totalCount; j++) {
			messageList.add(message.get(j));
		}
		PageBean<TbResource> pageBean = new PageBean<TbResource>(currentPage, pageSize, messageList, num.intValue(), totalCount);
		return pageBean;
	}

}
