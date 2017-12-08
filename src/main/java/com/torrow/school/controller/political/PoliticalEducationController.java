package com.torrow.school.controller.political;

import java.io.UnsupportedEncodingException;


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
import com.torrow.school.util.Garbled;

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
	 * @return 资源下载的上传和学生圆地和教师园地的上传
	 */
	@RequestMapping("uploadJumping")
	public String uploadJumping(Model model, int Pid) {
		categoryService.addBySelectPid(model, Pid);
		if(Pid==11) {
			return "politicaleducation/upload";
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
					if (item.getCaPid() == 9) {
						return this.uploadJumping(model, 9);
					} else {
						return this.uploadJumping(model, 11);
					}
				}
			}
		}
		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH表示24小时制；
		String Date = dFormat.format(date);
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String reContent = resourceService.uploadFile(file, path);
		TbUser tbUser=(TbUser)session.getAttribute("teacher");
		TbResource tb = new TbResource(item.getCaId(),tbUser.getUsName(), Date, item.getCaName(), file.getOriginalFilename(), reContent);
		resourceService.insert(tb);
		model.addAttribute("message", "添加成功");
		if (item.getCaPid() == 9) {
			return this.uploadJumping(model, 9);
		}
		return this.uploadJumping(model,11);
	}

	/**
	 * @param model 校园新闻类/上传/教育教研的管理
	 * @return 管理资源下载和通知公告和图片的的管理
	 */
	@RequestMapping("managePolitical")
	public String managePolitical(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			TbCategory tbCategory) {
		TbCategory record = new TbCategory();
		if(null!=tbCategory.getCaPid()) {
			record.setCaPid(tbCategory.getCaPid());
			model.addAttribute("zid", tbCategory.getCaPid());
		}
		if(null!=tbCategory.getCaId()) {
			record.setCaId(tbCategory.getCaId());
			model.addAttribute("zid", tbCategory.getCaId());
		}
		String str=tbCategory.getCaName();
		if (tbCategory.getCaName()!=null&&!tbCategory.getCaName().equals("")) {
			//这是为了用来解决中文乱码的问题
			Garbled g=new Garbled();
			String s=g.getEncoding(tbCategory.getCaName());
			if(s=="ISO-8859-1") {
				try {
					str=g.info(tbCategory.getCaName());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			record.setCaName(str);
			// session和model是不同的,接下来两个model是为了把数据返回到前台,以便分页进行使用
			model.addAttribute("caName", str);
		}
		// 携带的参数包括分页依据和查询条件还有显示条数
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 10));// 回显分页10条数据
		session.setAttribute("currentPage", currentPage);
		if(null!=tbCategory.getCaPid()&&tbCategory.getCaPid()==11) {
			return "politicaleducation/manageupload";
		}
		return "politicaleducation/downloadstudent";
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
