
package com.torrow.school.controller.visitor;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
	 * @param model
	 * @return
	 * 查看学校简介
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("viewGeneral")
	public ModelAndView viewGeneral(Integer gId,Model model) throws UnsupportedEncodingException{
		categoryService.getCategory(model);//将学校概括等封装进model
		TbCategory category = categoryService.selectByPrimaryKey(gId);
		if(category.getCaPid()==7){
//			List<TbResource> resourceList = resourceService.findingByPaging(1, record,10);
		}
		TbResource resource = resourceService.selectOne(gId);
		model.addAttribute("resource", resource);
		return new ModelAndView("visitor/schoolgeneral");
	}

	/**
	 * @param nName	得到新闻类别
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
		PageBean<TbResource> resourceLists = resourceService.findingByPaging(currentPage, record,10);
		categoryService.getCategory(model);//将概括，新闻等封装进model，供下拉菜单使用
		model.addAttribute("news", resourceLists);
		return new ModelAndView("visitor/schoolnews");
	}
}
