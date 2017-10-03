
package com.torrow.school.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbUser;

/**
 * @author 张金高
 *
 * 2017年10月2日上午9:10:26
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
		TbUser user = new TbUser("1@1","1","1","1",1,"12","123","123","12","ads","dsa");
//		user.setUsId(1);
		log.info("name"+name+" "+user);
		userService.insert(user);
		model.addAttribute("name", name);
		return "/index";
	}
	
}
