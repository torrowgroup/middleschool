package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.torrow.school.base.BaseController;
/**
 * @author anlijie
 *	
 *2017年10月5日上午10:46:56
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@RequestMapping("main")
    public String  main(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg", userService.findPage(currentPage));//回显分页数据
        return "admin/main";
    }
   
}
