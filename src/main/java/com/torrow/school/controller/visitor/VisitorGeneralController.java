
package com.torrow.school.controller.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	 */
	@RequestMapping("viewSchoolIntroduction")
	public String viewSchoolIntroduction(Model model){
		TbCategory record = new TbCategory();
		record.setCaName("学校简介");
		PageBean<TbResource> page = resourceService.findingByPaging(1, record);
		model.addAttribute("pagemsg", page);// 回显分页数据
		return "visitor/schoolintroduction";
	}
	
	
	/**
	 * @return
	 * 查看学校
	 */
	@RequestMapping("viewManageAuthorities")
	public String viewManageAuthorities(){
		return "";
	}
}
