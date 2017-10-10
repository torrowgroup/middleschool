package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbResource;

/**
 * @author 安李杰
 *
 * @2017年10月10日上午8:04:46
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @param model
	 * @param tbResource
	 * @return 资源类的添加
	 */
	@RequestMapping("addResource")
	public String addResource(Model model,TbResource tbResource){
		
		return "addresource";
	}
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 资源类的分页查看
	 */
	@RequestMapping("manageResource")
	public String manageResource(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg",resourceSerice.findPage(currentPage));//回显分页数据
		return "admin/manageResource";
	}
	
}
