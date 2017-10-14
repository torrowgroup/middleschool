
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
 *         2017年10月8日上午11:43:09
 */
/*
 * 留言板类controller
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 查看所有留言，分页
	@RequestMapping("manageMessage")
	public String manageMessage(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		model.addAttribute("messagePage", messageService.findPage(currentPage));// 回显分页数据
		return "admin/managemessage";
	}

	// 到达回复界面
	@RequestMapping("toAnswerMessage")
	public String toAnswerMessage(int id, Model model) {
		TbMessage message = messageService.selectByPrimaryKey(id);
		model.addAttribute("message", message);
		model.addAttribute("modeldata", "1212");
		return "admin/answermessage";
	}

	// 留言回复
	@RequestMapping("reply")
	public String reply(int id, String meReply, Model model) {
		int boo = messageService.reply(id, meReply);
		log.info("merely "+meReply);
		String reply = "回复成功";
		if (boo != 1) {
			reply = "回复失败";
		}
		model.addAttribute("msg",reply);
		return this.manageMessage(1,model);
	}
	// 留言回复
		@RequestMapping("deleteMessage")
		public String deleteMessage(int id,Model model) {
			int boo = messageService.deleteByPrimaryKey(id);
			String reply = "删除成功";
			if (boo != 1) {
				reply = "删除失败";
			}
			model.addAttribute("msg",reply);
			return this.manageMessage(1,model);
		}
}
