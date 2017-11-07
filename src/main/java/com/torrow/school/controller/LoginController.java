package com.torrow.school.controller;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbUser;
/**
 * @author 张金高
 * 2017年10月3日下午4:36:56
 */
@Controller
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
	@RequestMapping("userLogin")
	public String login(String usEmail,String usPassword,Model model){
		TbUser tbUser=userService.login(usEmail,usPassword);
		if(tbUser!=null) {
			model.addAttribute("msg", "登录成功");
			session.setAttribute("user",tbUser);
			int Pid=1;//概括类的
			List<TbCategory> list=categoryService.queryByPid(Pid);
			int id=7;//校园风光
			List<TbCategory> item=categoryService.queryByPid(id);
			int Pd = 2;//新闻类
			List<TbCategory> news = categoryService.queryByPid(Pd);
			model.addAttribute("newsList", news);
			model.addAttribute("generalList", list);
			model.addAttribute("sceneryList", item);
		}else{
			model.addAttribute("msg", "用户名或密码错误");
			return "index";
		}
		return "admin/index";
	}
	
	/**
	 * @return web-app下html用于跳转到登录界面
	 */
	@RequestMapping("login")
	public ModelAndView login(){
		log.info("登录");
		return new ModelAndView("index");
	}
	
	/**
	 * @return web-app下html用于跳转到游客首页界面
	 */
	@RequestMapping("index")
	public ModelAndView index(Model model){
		categoryService.getCategory(0,model);
		List<TbCategory> categoryList = categoryService.selectAll();
		resourceService.getResource(categoryList,model);//得到新闻，公告等
		return new ModelAndView("visitor/index");
	}
	
	
}
