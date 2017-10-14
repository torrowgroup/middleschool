package com.torrow.school.controller.manager;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
@Controller
@RequestMapping("/news")
public class SchoolNewsController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping("newsJumping")
	public String newsJumping() {
		return "admin/addschoolnews";
	}
	
	@RequestMapping("addSchoolNews")
	public String addSchoolNews(Model model,String reTitle,String reContent,String caName){
		Date d=new Date();
		List<TbCategory> tbCategory=categoryService.selectAll();
		for(TbCategory item:tbCategory){
			if(item.getCaPid()==2){
				if(caName.equals(item.getCaName())){
					TbResource record=new TbResource(item.getCaId(),d,caName,reTitle,reContent);
					resourceService.insert(record);
					model.addAttribute("message","添加成功");
				}else{
					model.addAttribute("message","不存在该类别,添加失败");
				}
			}
		}
		return "admin/index";
	}
}
