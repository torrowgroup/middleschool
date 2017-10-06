package com.torrow.school.controller.manager;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbUser;


/**
 * @author 安李杰
 *
 * @2017年10月6日下午5:33:50
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 查找所有用户，分页
	@RequestMapping("manageUser")
	public String manageUser(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		model.addAttribute("pagemsg", userService.findPage(currentPage));// 回显分页数据
		return "admin/manageuser";
	}

	// 到达添加用户界面
	@RequestMapping("toAddUser")
	public String toAddUser(Model model) {
		List<TbCategory> categoryList = categoryService.selectAll();
		int pId;
		for (int i=0;i<categoryList.size();i++) {
			pId = categoryList.get(i).getCaPid(); 
			if(pId==3||pId==4||pId==5){		//得到所有身份类别,删除所有非身份类
				continue;
			}
			categoryList.remove(i);
			i--;
		}
		model.addAttribute("categoryList", categoryList);
		return "admin/adduser";
	}

	// 到达添加用户界面
	@RequestMapping("addUser")
	public String addUser(TbUser tbUser) {
		
		return "admin/adduser";
	}
}
