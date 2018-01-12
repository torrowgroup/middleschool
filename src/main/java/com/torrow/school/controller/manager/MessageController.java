
package com.torrow.school.controller.manager;

import java.io.UnsupportedEncodingException;


import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String manageMessage(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, String inquiry,Model model) throws UnsupportedEncodingException {
		if(inquiry!=null){
			inquiry = new String(inquiry.getBytes("iso8859-1"),"utf-8");
		}
		model.addAttribute("messagePage", messageService.findPage(currentPage,inquiry,7));// 回显分页数据
		model.addAttribute("inquiry", inquiry);
		return "admin/message/managemessage";
	}

	// 到达回复界面
	@RequestMapping("toAnswerMessage")
	public String toAnswerMessage(int id,int page, Model model) {
		TbMessage message = messageService.selectByPrimaryKey(id);
		model.addAttribute("message", message);
		model.addAttribute("page", page);
		return "admin/message/answermessage";
	}

	// 留言回复
	@RequestMapping("reply")
	public String reply(int id, String meReply, int page,Model model) throws UnsupportedEncodingException {
		int boo = messageService.reply(id, meReply);
		String reply = "回复成功";
		if (boo != 1) {
			reply = "回复失败";
		}
		model.addAttribute("msg", reply);
		return this.manageMessage(page,null, model);
	}

	// 删除留言
	@RequestMapping("deleteMessage")
	public String deleteMessage(int id, int page,Model model) throws UnsupportedEncodingException {
		int boo = messageService.deleteByPrimaryKey(id);
		String reply = "删除成功";
		if (boo != 1) {
			reply = "删除失败";
		}
		model.addAttribute("msg", reply);
		return this.manageMessage(page,null, model);
	}

	// 用于富文本编辑器的图片上传
	@RequestMapping("uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response) throws Exception {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String fileName = messageService.uploadImg(file,path);
		// 返回图片的URL地址
		response.getWriter().write(session.getServletContext().getContextPath()+"/static/uploadimg/"+ fileName);
	}
}
