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
 * @2017年11月25日下午8:22:24
 * 
 * 政教处的上传
 */
@Controller
@RequestMapping("/political")
public class PoliticalEducationController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param model
	 * @param Pid
	 * @return 政教处的上传
	 */
	@RequestMapping("uploadJumping")
	public String addNewsJumping(Model model,int Pid) {
		categoryService.addBySelectPid(model,Pid);
		return "politicaleducation/uploadfile";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 政教处上传类的查看
	 */
	@RequestMapping("manageEducationUpload")
	public String manageUpload(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			Integer id) {
		TbCategory record = new TbCategory();
		record.setCaId(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 10));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("zid", id);
		return "politicaleducation/manageupload";
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
