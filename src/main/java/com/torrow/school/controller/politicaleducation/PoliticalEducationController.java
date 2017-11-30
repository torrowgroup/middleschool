package com.torrow.school.controller.politicaleducation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.entity.TbUser;

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
	public String uploadJumping(Model model) {
		TbUser tbUser=(TbUser)session.getAttribute("political");
		List<TbCategory> list=categoryService.selectAll();
		boolean enough=false;
		if(!list.isEmpty()) {
			for(TbCategory item:list) {
				if(tbUser.getCaName().equals(item.getCaName())) {
					model.addAttribute("TbCategory",item);
					enough=true;
				}
			}
		}
		if(enough==false) {
			model.addAttribute("message","请先去类别类中添加相应类别");
			return "politicaleducation/empty";
		}
		return "politicaleducation/uploadfile";
	}
	
	/**
	 * @param tbResource
	 * @param picture
	 * @param model
	 * @return 上传的操作
	 * @throws Exception
	 */
	@RequestMapping("upload")
	public String upload(TbResource tbResource, MultipartFile file, Model model) throws Exception {
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		List<TbResource> resource = resourceService.selectAll();
		for (TbResource en : resource) {
			if (en.getCaId() == tbResource.getCaId()) {
				if (file.getOriginalFilename().equals(en.getReContent())) {
					model.addAttribute("message", "该文件已存在,上传失败");
					return this.uploadJumping(model);
				}
			}
		}
		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH表示24小时制；
		String Date = dFormat.format(date);
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String reContent = resourceService.uploadFile(file, path);
		TbResource tb = new TbResource(item.getCaId(), Date, item.getCaName(), file.getOriginalFilename(), reContent);
		resourceService.insert(tb);
		model.addAttribute("message", "添加成功");
		return this.uploadJumping(model);
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
