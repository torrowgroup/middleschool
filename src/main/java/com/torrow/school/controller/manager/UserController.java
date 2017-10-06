package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.torrow.school.base.BaseController;

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
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 用户信息的分页处理
	 */
	@RequestMapping("main")
    public String  main(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg", userService.findByPage(currentPage));//回显分页数据
        return "admin/main";
    }
   
}
