
package com.torrow.school.controller.visitor;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbResource;

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
	public ModelAndView viewSchoolIntroduction(String gName,Model model) throws UnsupportedEncodingException{
		gName = new String(gName.getBytes("iso-8859-1"),"UTF-8");
		TbResource resource = resourceService.selectOne(gName);
		categoryService.getCategory(model);//将学校概括等封装进model
		model.addAttribute("resource", resource);
		return new ModelAndView("visitor/schoolgeneral");
	}
	
}
