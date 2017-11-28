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
		if (null != tbCategory) {
			model.addAttribute("message", "该类别已存在");
		} else {
			if (caPid == 8 && categoryService.queryByPid(8).size() > 4) {
				model.addAttribute("message", "底部链接最多有4个");
				return "admin/category/addcategory";
			}
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
		model.addAttribute("pagemsg", categoryService.findPage(currentPage, 2));// 回显分页数据
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
	public String updateCategory(Model model, TbCategory tbCategory) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbCategory t = categoryService.selectCaName(tbCategory.getCaName());
		if (null != t) {
			model.addAttribute("message", "该类别已存在,修改失败");
		} else {
			TbCategory record = categoryService.selectByPrimaryKey(tbCategory.getCaId());
			if (!record.getCaName().equals("管理员")) {
				record.setCaName(tbCategory.getCaName());
				categoryService.updateByPrimaryKey(record);// 这是类别类的修改
				TbResource tbResource = new TbResource(tbCategory.getCaId(), tbCategory.getCaName());
				resourceService.updateDeleteTbResourceByCaId(tbResource, 1);// 这是资源类的修改
				TbUser tbUser = new TbUser(tbCategory.getCaId(), tbCategory.getCaName());
				userService.updateDeleteUserByCaId(tbUser, 1);// 这是用户类的修改
				model.addAttribute("message", "修改成功");
			} else {
				model.addAttribute("message", "不可以对管理员进行操作");
			}
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
		TbCategory tbCategory = categoryService.selectByPrimaryKey(id);
		if (!tbCategory.getCaName().equals("管理员")) {
			categoryService.deleteByPrimaryKey(id);// 这是删除类别类
			TbResource tbResource = new TbResource();
			tbResource.setCaId(id);
			resourceService.updateDeleteTbResourceByCaId(tbResource, 2);// 删除资源类对应的
			TbUser tbUser = new TbUser();
			tbUser.setCaId(id);
			userService.updateDeleteUserByCaId(tbUser, 2);// 删除用户对应的
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "你不可以对管理员进行操作");
		}
		return this.manageCategory(currentPage, model);
	}

}
