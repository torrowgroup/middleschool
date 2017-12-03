package com.torrow.school.controller.visitor;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;

/**
 * @author 张金高
 *
 * 2017年10月27日上午11:43:45
 * 
 * 游客浏览概括类新闻类控制层
 */
@Controller
@RequestMapping("/visitorGN")
public class GeneralNewsController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param currentPage 查看校园风光时用于分页
	 * @param gId	得到用户选中的功能项
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("viewGeneral")
	public ModelAndView viewGeneral(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,Integer gId,Model model) throws ParseException {
		categoryService.getCategory(gId,model);//将学校概括等菜单项封装进model，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		TbCategory category = categoryService.selectByPrimaryKey(gId);
		log.info("--"+category);
		if(category.getCaPid()==7){	 //查看校园风光时调用
			TbCategory categoryInquiry = new TbCategory();
			categoryInquiry.setCaId(category.getCaId());
			PageBean<TbResource> resourceList = resourceService.findingByPaging(currentPage, categoryInquiry,6);
			model.addAttribute("views", resourceList);
			return new ModelAndView("visitor/schoolviews");
		}
		TbResource resource = resourceService.selectOne(gId);
		model.addAttribute("resource", resource);
		return new ModelAndView("visitor/schoolgeneral");
	}

	/**
	 * @param pId	获得图片资源类的id
	 * @param nId	接受用户查看的功能
	 * @param model
	 * @return  查看校园风光的详情
	 * @throws ParseException 
	 */
	@RequestMapping("viewsDetails")
	public ModelAndView viewsDetails(Integer pId,Integer nId,Model model) throws ParseException {
		resourceService.getTimeInfor(model);//得到考试和联系信息
		TbResource resource = resourceService.selectByPrimaryKey(pId);
		model.addAttribute("resource", resource);//将图片放进model
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		return new ModelAndView("visitor/viewdetails");
	}
	
	/**
	 * @param nName	得到新闻类别
	 * @param nId	接受用户查看的功能
	 * @param model
	 * @return
	 * @throws ParseException 
	 * 查看新闻
	 */
	@RequestMapping("viewNews")
	public ModelAndView viewNews(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer nId) throws ParseException {
		TbCategory record = new TbCategory();
		record.setCaId(nId);
		PageBean<TbResource> resourceLists = resourceService.findingByPaging(currentPage, record,10);
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		model.addAttribute("news", resourceLists);
		return new ModelAndView("visitor/schoolnews");
	}
	
	/**
	 * @param rId	接受用户查看的具体的新闻id
	 * @param nId	接受用户查看的功能
	 * @param model
	 * @return 		查看新闻详情
	 */
	@RequestMapping("newDetails")
	public ModelAndView newDetails(Integer rId,Integer nId,Model model) throws ParseException{
		TbResource resource = resourceService.selectByPrimaryKey(rId);
		int pId = categoryService.selectByPrimaryKey(resource.getCaId()).getCaPid();//得到所选择的资源类的pId，如果是学生管理和教师发展提供下载
		if (pId==9) {
			model.addAttribute("download", true);
		}
		model.addAttribute("resource", resource);
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		return new ModelAndView("visitor/newdetails");
	}
	
	
	/**
	 * @param model
	 * @return	首页更多新闻,到达新闻界面
	 */
	@RequestMapping("moreNews")
	public ModelAndView moreNews(Model model) throws ParseException{
		int pId = 2;//新闻类的id
		List<TbCategory> categoryList = categoryService.queryByPid(pId);
		int caId = 0;
		if(categoryList!=null){
			TbCategory category = categoryList.get(0);
			caId = category.getCaId();
		}
		return this.viewNews(1,model, caId);
	}
	
	/**
	 * @param id 下载文件id
	 * @throws Exception
	 * 下载学生管理和教师成长的内容 
	 */
	@RequestMapping("downloadNews")
	public void downloadNews(int id,HttpServletResponse response) throws Exception{
		resourceService.down(request, response, id);
	}
}
