package com.torrow.school.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		if(tbUser!=null) {
			model.addAttribute("msg", "登录成功");
		}else{
			model.addAttribute("msg", "用户名或密码错误");
		}
		return "index";
	}

}
