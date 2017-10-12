package com.torrow.school.controller.manager;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;

/**
 * @author 安李杰
 *
 * @2017年10月10日上午8:04:46
 */
@Controller
@RequestMapping("/general")
public class GeneralController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 资源类的分页查看
	 */
	@RequestMapping("manageResource")
	public String manageResource(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg",resourceService.findPage(currentPage));//回显分页数据
		return "admin/manageResource";
	}
	
	/**
	 * @param model
	 * @return 对于资源类的添加跳转
	 */
	@RequestMapping("generalJumping")
	public String resourcejumping(Model model){
		return "admin/addgeneral";
	}
	
	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 概括类的添加
	 */
	@RequestMapping("addGeneral")
	public String addGeneral(Model model,String reTitle,String reContent,String caName){
		List<TbCategory> tbCategory=categoryService.selectAll();
		for(TbCategory item:tbCategory){
			if(item.getCaPid()==1){
				if(caName.equals(item.getCaName())){
					TbResource record=new TbResource(item.getCaId(),caName,reTitle,reContent);
					resourceService.insert(record);
				}else{
					model.addAttribute("message","不存在该类别，添加失败");
				}
			}
		}
		return "add/index";
	}
	
}
