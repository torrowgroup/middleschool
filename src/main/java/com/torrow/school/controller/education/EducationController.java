package com.torrow.school.controller.education;

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
	@RequestMapping("uploadEducation")
	public String uploadEducation(Model model) {
		TbUser tbUser=(TbUser)session.getAttribute("education");
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
			return "educationoffice/empty";
		}
		return "educationoffice/uploadfile";
	}

	@RequestMapping("schoolLiterature")
	public String schoolLiterature(Model model) {
		TbUser tbUser=(TbUser)session.getAttribute("education");
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
			return "educationoffice/empty";
		}
		return "educationoffice/uploadfile";
	}
	/**
	 * @param currentPage
	 * @param model
	 * @return 教研组上传类的查看
	 */
	@RequestMapping("manageEducationUpload")
	public String manageUpload(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			TbCategory tbCategory) {
		TbCategory record = new TbCategory();
		//这步是一般查询
		record.setCaId(tbCategory.getCaId());
		//这步是为了模糊查询
		if(null!=tbCategory.getCaName()) {
			record.setCaName(tbCategory.getCaName());
		}
		//携带的参数包括分页依据和查询条件还有显示条数
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 10));// 回显分页10条数据
		session.setAttribute("currentPage", currentPage);
		session.setAttribute("caName", tbCategory.getCaName());
		//session和model是不同的,接下来两个model是为了把数据返回到前台,以便分页进行使用
		model.addAttribute("caName", tbCategory.getCaName());
		model.addAttribute("zid", tbCategory.getCaId());
		return "educationoffice/manageupload";
	}
	
	/**
	 * @param tbResource
	 * @param picture
	 * @param model
	 * @return 教研组上传的操作
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
						return this.uploadEducation(model);
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
		return this.uploadEducation(model);
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
