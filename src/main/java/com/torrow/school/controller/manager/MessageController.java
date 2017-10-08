
package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbMessage;

/**
 * @author 张金高
 *
 * 2017年10月8日上午11:43:09
 */
/*
 * 留言板类controller
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//查看所有留言，分页
	@RequestMapping("manageMessage")
	public String manageMessage(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		model.addAttribute("messagePage", messageService.findPage(currentPage));// 回显分页数据
		return "admin/managemessage";
	}
	
	//到达回复界面
	@RequestMapping("answerMessage")
	public String answerMessage(int id,Model model){
		TbMessage message = messageService.selectByPrimaryKey(id);
		model.addAttribute("message", message);
		return "admin/answermessage";
	}

	
}
