
package com.torrow.school.controller.visitor;

import java.io.UnsupportedEncodingException;

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
 * 游客浏览概括类
 */
@Controller
@RequestMapping("/visitor")
public class VisitorGeneralController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param currentPage 查看校园风光时用于分页
	 * @param gId	得到用户选中的功能项
	 * @param model
	 * @return
	 */
	@RequestMapping("viewGeneral")
	public ModelAndView viewGeneral(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,Integer gId,Model model) {
		categoryService.getCategory(gId,model);//将学校概括等菜单项封装进model，以及用户选择的功能项
		TbCategory category = categoryService.selectByPrimaryKey(gId);
		if(category.getCaPid()==7){	 //查看校园风光时调用
			PageBean<TbResource> resourceList = resourceService.findingByPaging(currentPage, category,6);
			model.addAttribute("views", resourceList);
			return new ModelAndView("visitor/schoolviews");
		}
		TbResource resource = resourceService.selectOne(gId);
		model.addAttribute("resource", resource);
		return new ModelAndView("visitor/schoolgeneral");
	}

	/**
	 * @param nName	得到新闻类别
	 * @param nId	接受用户查看的功能
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 * 查看新闻
	 */
	@RequestMapping("viewNews")
	public ModelAndView viewNews(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer nId) {
		TbCategory record = new TbCategory();
		record.setCaId(nId);
		PageBean<TbResource> resourceLists = resourceService.findingByPaging(currentPage, record,2);
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		model.addAttribute("news", resourceLists);
		return new ModelAndView("visitor/schoolnews");
	}
	
	/**
	 * @param pId	获得图片资源类的id
	 * @param nId	接受用户查看的功能
	 * @param model
	 * @return  查看校园风光的详情
	 */
	@RequestMapping("viewsDetails")
	public ModelAndView viewsDetails(Integer pId,Integer nId,Model model) {
		TbResource resource = resourceService.selectByPrimaryKey(pId);
		model.addAttribute("resource", resource);//将图片放进model
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		return new ModelAndView("visitor/viewdetails");
	}
	
	/**
	 * @param rId	接受用户查看的具体的新闻id
	 * @param nId	接受用户查看的功能
	 * @param model
	 * @return 		查看新闻详情
	 */
	@RequestMapping("newDetails")
	public ModelAndView newDetails(Integer rId,Integer nId,Model model){
		TbResource resource = resourceService.selectByPrimaryKey(rId);
		model.addAttribute("resource", resource);
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		return new ModelAndView("visitor/newdetails");
	}
}
