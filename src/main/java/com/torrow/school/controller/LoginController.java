package com.torrow.school.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbUser;

/**
 * 
 * @author 张金高
 *
 * 2017年10月3日下午4:36:56
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param usEmail
	 * @param usPassword
	 * @param model
	 * @return 登陆的方法
	 */
	@RequestMapping("login")
	public String login(String usEmail,String usPassword,Model model){
		
		TbUser tbUser=userService.login(usEmail,usPassword);
		log.info("--"+tbUser);
		if(tbUser!=null) {
			model.addAttribute("msg", "登录成功");
		}else{
			model.addAttribute("msg", "用户名或密码错误");
		}
		return "index";
	}
	
}