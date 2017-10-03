
package com.torrow.school.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbUser;

/**
 * @author 寮犻噾楂�
 *
 * 2017骞�10鏈�2鏃ヤ笂鍗�9:10:26
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping(value="hello",method=RequestMethod.GET)	
	public String hello(@RequestParam("name") String name,Model model){
		TbUser user = new TbUser(1,"1@1","1","1","1",1,"12","123","123","12","ads","dsa");
//		user.setUsId(1);
		log.info("name"+name+" "+user);
		userService.insert(user);
		model.addAttribute("name", name);
		return "/index";
	}
	
	@RequestMapping("login")
	public String login(String usEmail,String usPassword,Model model){
		
		System.out.println("用户登录："+usEmail+usPassword);
		
		TbUser tbUser=userService.login(usEmail,usPassword);
		if(tbUser!=null) {
			model.addAttribute("msg", "登录成功");
		}else{
			model.addAttribute("msg", "用户名或密码错误");
		}
		return "index";
	}
	
}
