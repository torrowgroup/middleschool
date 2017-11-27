package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param model
	 * @param Pid
	 * @return 教师上传的上传
	 */
	@RequestMapping("uploadJumping")
	public String addNewsJumping(Model model,int Pid) {
		categoryService.addBySelectPid(model,Pid);
		return "teacher/uploadfile";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 上传类的管理
	 */
	@RequestMapping("manageUpload")
	public String manageUpload(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			Integer id) {
		TbCategory record = new TbCategory();
		record.setCaId(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 10));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("zid", id);
		return "teacher/manageupload";
	}

}
