package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@RequestMapping("categoryjumping")
	public String jumping(){
		log.info("跳转");
		return "admin/addcategory";	
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
		return "admin/addcategory";
	}
	
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 类别类的分页显示
	 */
	@RequestMapping("/manageCategory")
    public String  manageCategory(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg", categoryService.findPage(currentPage));//回显分页数据
        return "admin/managecategory";
    }
	
	/**
	 * @param id
	 * @param model
	 * @return 根据id来查看类别信息
	 */
	@RequestMapping("/selectOneCategory")
	public String selectOneCategory(Integer id,Model model) {
		TbCategory category=categoryService.selectByPrimaryKey(id);
		model.addAttribute("category",category);
		return "admin/updatecategory";
	}
	
	/**
	 * @param model
	 * @param caName
	 * @return 修改类别信息
	 */
	@RequestMapping("/updateCategory")
	public String updateCategory(Model model,String caName,Integer id) {
		TbCategory tbCategory=categoryService.selectCaName(caName);
		if(tbCategory!=null) {
			model.addAttribute("message","该类别已存在,修改失败");
			return "admin/updatecategory";
		}else{
			TbCategory record=categoryService.selectByPrimaryKey(id);
			record.setCaName(caName);
			categoryService.updateByPrimaryKey(record);
			model.addAttribute("message","修改成功");
		}
		return "admin/index";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 根据id来删除类别类
	 */
	@RequestMapping("/deleteCategory")
	public String deleteCategory(Model model,Integer id) {
		log.info("P"+id);
		categoryService.deleteByPrimaryKey(id);
		model.addAttribute("message","删除成功");
		return "admin/index";
	}
}
