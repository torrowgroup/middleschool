package com.torrow.school.controller.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;

/**
 * @author 安李杰
 *
 * @2017年11月25日下午5:11:25
 * 
 * 此处为教研组管理后台
 */
@Controller
@RequestMapping("/education")
public class EducationController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param model
	 * @param Pid
	 * @return 教研组的上传
	 */
	@RequestMapping("addNewsJumping")
	public String addNewsJumping(Model model,int Pid) {
		categoryService.addBySelectPid(model,Pid);
		return "educationoffice/addschoolnews";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 教研组上传类的查看
	 */
	@RequestMapping("manageEducationUpload")
	public String manageUpload(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			Integer id) {
		TbCategory record = new TbCategory();
		// record.setCaPid(2);
		record.setCaId(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 10));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("zid", id);
		return "educationoffice/manageupload";
	}
	
	/**
	 * 文件下载功能
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/down")
	public void down(HttpServletRequest request, HttpServletResponse response, int id) throws Exception {
		resourceService.down(request, response, id);
	}
}
