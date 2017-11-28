package com.torrow.school.controller.manager;

import java.io.File;
import java.io.IOException;
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

/**
 * @author 安李杰 这里边有校园新闻和通知公告、教育教研类的管理也在这一块、校园文学也在这一块
 * @2017年11月2日上午8:26:18
 */
@Controller
@RequestMapping("/news")
public class SchoolNewsController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param model
	 * @return 添加校园新闻、上传（学生管理、教师成长）、 教研组上的上传、资源下载类的上传、校园文学类的上传、学校公告类的添加
	 */
	@RequestMapping("addNewsJumping")
	public String addNewsJumping(Model model, int Pid) {
		categoryService.addBySelectPid(model, Pid);
		if (Pid == 9 || Pid == 3 || Pid == 11 || Pid == 12) {
			return "admin/schoolnews/uploadfile";
		}
		return "admin/schoolnews/addschoolnews";
	}

	/**
	 * @param model
	 * @param id
	 * @return 删除文件的
	 */
	@RequestMapping("deleteFile")
	public String deleteScenery(Model model, Integer id) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if(null!=tb) {
			String path = session.getServletContext().getRealPath("static/uploadimg") + "/" + tb.getReContent();
			File files = new File(path);
			if (files.exists()) {
				files.delete();
			}
			String msg = "删除失败";
			if (resourceService.deleteByPrimaryKey(id) == 1) {
				msg = "删除成功";
			}
			model.addAttribute("message", msg);
		} else {
			model.addAttribute("message","这个文件不存在");
			return "admin/empty";
		}
		return this.manageUpload(currentPage, model, tb.getCaId());
	}

	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 添加学校新闻
	 */
	@RequestMapping("addSchoolNews")
	public String addSchoolNews(Model model, TbResource tbResource) {
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		List<TbResource> resource = resourceService.selectAll();
		for (TbResource en : resource) {
			if (en.getCaId() == tbResource.getCaId()) {
				if (en.getReTitle().equals(tbResource.getReTitle())) {
					model.addAttribute("message", "该标题已存在，添加失败");
					if (item.getCaPid() == 2) {
						return this.addNewsJumping(model, 2);
					} else {
						return this.addNewsJumping(model, 6);
					}
				}
			}
		}
		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH表示24小时制；
		String Date = dFormat.format(date);
		if (null != item) {
			TbResource record = new TbResource(item.getCaId(), Date, item.getCaName(), tbResource.getReTitle(),
					tbResource.getReContent());
			int i = resourceService.insert(record);
			if (i != 0) {
				model.addAttribute("message", "添加成功");
			} else {
				model.addAttribute("message", "添加失败");
			}
		} else {
			model.addAttribute("message", "添加失败,不存在该类别");
		}
		if (item.getCaPid() == 6) {
			return this.addNewsJumping(model, 6);
		}
		return this.addNewsJumping(model, 2);
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 校园新闻类/学校公告的管理
	 */
	@RequestMapping("manageSchoolNews")
	public String manageSchoolNews(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model, Integer id) {
		TbCategory record = new TbCategory();
		record.setCaId(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 10));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("zid", id);
		return "admin/schoolnews/manageschoolnews";
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
		return "admin/schoolnews/manageupload";
	}

	/**
	 * @param id
	 * @param model
	 * @return 查询一条新闻
	 */
	@RequestMapping("selectOneNews")
	public String selectOneNews(int id, Model model) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if (null != tb) {
			model.addAttribute("tbResource", tb);
		} else {
			model.addAttribute("message", "该名称不存在");
			return "admin/empty";
		}
		TbCategory tbCategory = categoryService.selectByPrimaryKey(tb.getCaId());
		if (tbCategory.getCaPid() == 6) {
			model.addAttribute("sign", 1);
		}
		return "admin/schoolnews/managenews";
	}

	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @return 修改学校新闻
	 */
	@RequestMapping("updateSchoolNews")
	public String updateGeneral(Model model, @RequestParam(value = "picture", required = false) MultipartFile[] picture,
			TbResource tbResource) throws IllegalStateException, IOException {
		TbResource tb = resourceService.selectByPrimaryKey(tbResource.getReId());
		if (null != tb) {
			tb.setReContent(tbResource.getReContent());
			tb.setReTitle(tbResource.getReTitle());
			int i = resourceService.updateByPrimaryKey(tb);
			if (i != 0) {
				model.addAttribute("message", "保存成功");
			} else {
				model.addAttribute("message", "保存失败");
			}
		} else {
			model.addAttribute("message","这条新闻不存在");
			return "admin/empty";
		}
		return this.selectOneNews(tbResource.getReId(), model);
	}

	/**
	 * @param model
	 * @param
	 * @return 删除学校新闻
	 */
	@RequestMapping("deleteNews")
	public String deleteNews(Model model, int id) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if(null!=tb) {
			int i = resourceService.deleteByPrimaryKey(id);
			if (i != 0) {
				model.addAttribute("message", "删除成功");
			} else {
				model.addAttribute("message", "删除失败");
			}
		} else {
			model.addAttribute("message", "这条新闻不存在");
			return "admin/empty";
		}
		return this.manageSchoolNews(currentPage, model, tb.getCaId());
	}

	/**
	 * @param model
	 * @return 管理资源下载的管理
	 */
	@RequestMapping("manageObject")
	public String manageObject(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer id) {
		TbCategory record = new TbCategory();
		record.setCaPid(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 4));// 回显分页数据
		model.addAttribute("sign", 1);// 为了在查看管理轮播图时是同一个界面
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("zid", id);
		if(id==6) {
			return "admin/notice/managenotice";
		}else if(id==10) {
			return "admin/managepicture/managepicture";
		}
		return "admin/download/manageupload";
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
						return this.addNewsJumping(model, 9);
					} else if (item.getCaPid() == 11) {
						return this.addNewsJumping(model, 11);
					} else if (item.getCaPid() == 12) {
						return this.addNewsJumping(model, 12);
					} else if (item.getCaPid() == 3) {
						return this.addNewsJumping(model, 3);
					}
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
		// 校园文学
		if (item.getCaPid() == 12) {
			return this.addNewsJumping(model, 12);
		}
		// 教育教研
		if (item.getCaPid() == 3) {
			return this.addNewsJumping(model, 3);
		}
		// 资源类的下载
		if (item.getCaPid() == 11) {
			return this.addNewsJumping(model, 11);
		}
		return this.addNewsJumping(model, 9);
	}

	// 用于富文本编辑器的图片上传
	@RequestMapping("uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response) throws Exception {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String fileName = resourceService.uploadPicture(file, path);
		// 返回图片的URL地址
		response.getWriter().write("/middleschool/static/uploadimg/" + fileName);
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
