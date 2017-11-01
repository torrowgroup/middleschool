package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.entity.TbUser;

/**
 * 这个控制器是对类别进行管理的
 * 
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
	public String jumping() {
		log.info("跳转");
		return "admin/category/addcategory";
	}

	/**
	 * @param caName
	 * @param caPid
	 * @return 用于执行添加类别类的操作
	 */
	@RequestMapping("/addCategory")
	public String addCategory(String caName, Integer caPid, Model model) {
		TbCategory tbCategory = categoryService.selectCaName(caName);
		if (null!=tbCategory) {
			model.addAttribute("message", "该类别已存在");
		} else {
			TbCategory record = new TbCategory(caPid, caName);
			categoryService.insert(record);
			model.addAttribute("message", "添加成功");
		}
		return "admin/category/addcategory";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 类别类的分页显示
	 */
	@RequestMapping("/manageCategory")
	public String manageCategory(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model) {
		model.addAttribute("pagemsg", categoryService.findPage(currentPage,2));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		return "admin/category/managecategory";
	}

	/**
	 * @param id
	 * @param model
	 * @return 根据id来查看类别信息
	 */
	@RequestMapping("/selectOneCategory")
	public String selectOneCategory(Integer id, Model model) {
		TbCategory category = categoryService.selectByPrimaryKey(id);
		model.addAttribute("category", category);
		return "admin/category/updatecategory";
	}

	/**
	 * @param model
	 * @param caName
	 * @return 修改类别信息
	 */
	@RequestMapping("/updateCategory")
	public String updateCategory(Model model, String caName, Integer id) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbCategory tbCategory = categoryService.selectCaName(caName);
		if (null!=tbCategory) {
			model.addAttribute("message", "该类别已存在,修改失败");
		} else {
			TbCategory record = categoryService.selectByPrimaryKey(id);
			record.setCaName(caName);
			categoryService.updateByPrimaryKey(record);
			TbResource tb = resourceService.selectByCaId(id);
			if (null!=tb) {
				tb.setCaName(caName);
				resourceService.updateByPrimaryKey(tb);
			}
			TbUser user = userService.selectByCaId(id);
			if (null!=user) {
				user.setCaName(caName);
				userService.updateByPrimaryKey(user);
			}
			model.addAttribute("message", "修改成功");
		}
		return this.manageCategory(currentPage, model);
	}

	/**
	 * @param model
	 * @param id
	 * @return 根据id来删除类别类
	 */
	@RequestMapping("/deleteCategory")
	public String deleteCategory(Model model, Integer id) {
		int currentPage = (int) session.getAttribute("currentPage");
		categoryService.deleteByPrimaryKey(id);
		TbResource tb = resourceService.selectByCaId(id);
		if (null!=tb) {
			resourceService.deleteByCaId(id);
		}
		TbUser user = userService.selectByCaId(id);
		if (null!=user) {
			user.setCaId(0);
			userService.updateByPrimaryKey(user);
		}
		model.addAttribute("message", "删除成功");
		return this.manageCategory(currentPage, model);
	}

}
