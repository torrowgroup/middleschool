package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;

/**
 * 这个控制器是对类别进行管理的
 * @author 安李杰
 *
 * @2017年10月6日下午5:33:57
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @return 用于跳转到添加类别类
	 */
	@RequestMapping("jumping")
	public String jumping(){
		log.info("跳转");
		return "admin/addCategory";	
	}
	
	/**
	 * @param caName
	 * @param caPid
	 * @return 用于执行添加类别类的操作
	 */
	@RequestMapping("/addCategory")
	public String addCategory(String caName,Integer caPid,Model model){
		log.info("测试"+caName+caPid);
		TbCategory tbCategory=categoryService.selectCaName(caName);
		if(tbCategory!=null) {
			model.addAttribute("message","该类别已存在");
			return "admin/addCategory";
		}else {
			TbCategory record=new TbCategory(caPid,caName);
			categoryService.insert(record);
			model.addAttribute("message","添加成功");
		}	
		return "admin/addCategory";
	}
}
