package com.torrow.school.controller.manager;

import java.io.File;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
 * @author 安李杰 管理概括类、管理轮播图
 *
 * @2017年10月10日上午8:04:46
 */
@Controller
@RequestMapping("/general")
public class GeneralController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param currentPage
	 * @param model
	 * @return 管理学校风光
	 */
	@RequestMapping("manageScenery")
	public String manageScenery(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		TbCategory record = new TbCategory();
		record.setCaPid(7);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record,4));// 回显分页数据
		model.addAttribute("sign", 1);// 为了在查看管理校园风光时是同一个界面
		session.setAttribute("currentPage", currentPage);
		return "admin/general/managescenery";
	}
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 管理图片轮播图
	 */
	@RequestMapping("managePicture")
	public String managePicture(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		TbCategory record = new TbCategory();
		record.setCaPid(10);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record,4));// 回显分页数据
		model.addAttribute("sign", 1);// 为了在查看管理轮播图时是同一个界面
		session.setAttribute("currentPage", currentPage);
		return "admin/managepicture/managepicture";
	}
	
	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 对于概括类进行修改的操作
	 */
	@RequestMapping("updateGeneral")
	public String updateGeneral(Model model, @RequestParam(value = "picture", required = false) MultipartFile[] picture,
			TbResource tbResource) throws IllegalStateException, IOException {
		if(tbResource.getReContent().equals(null)) {
			int i=resourceService.deleteByPrimaryKey(tbResource.getReId());
			if(i!=0) {
				model.addAttribute("message", "保存成功");
			}else {
				model.addAttribute("message", "保存失败");
			}
		}
		TbResource tb = resourceService.selectByPrimaryKey(tbResource.getReId());
		if (null != tb) {
			tb.setReContent(tbResource.getReContent());
			int i=resourceService.updateByPrimaryKey(tb);
			if(i!=0) {
				model.addAttribute("message", "保存成功");
			}else {
				model.addAttribute("message", "保存失败");
			}
		} 
		return this.manageGeneralJumping(model);
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 查询一条信息
	 */
	@RequestMapping("selectOneScenery")
	public String selectOneScenery(Model model,Integer id) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		model.addAttribute("tb",tb);
		return "admin/general/updateschoolscenery";
	}
	/**
	 * @return 删除概括类
	 *//*
	@RequestMapping("deleteGeneral")
	public String deleteGeneral(Model model, int id) {
		int currentPage = (int) session.getAttribute("currentPage");
		int i = resourceService.deleteByPrimaryKey(id);
		if (i != 0) {
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "删除失败");
		}
		//return this.manageGeneral(currentPage, model, caName);
		return  "";
	}*/

	/**
	 * @param model
	 * @return 管理学校简介的跳转
	 */
	@RequestMapping("manageGeneralJumping")
	public String manageGeneralJumping(Model model) {
		int Pid=1;
		int id=7;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		List<TbCategory> item=categoryService.queryByPid(id);
		if(!list.isEmpty()&&!item.isEmpty()) {
			model.addAttribute("categoryList", list);
			model.addAttribute("sceneryList", item);
		}else {
			model.addAttribute("message", "该类别名称不存在");
		}
		return "admin/general/index";
	}

	/**
	 * @param model
	 * @return 图片类的上传
	 */
	@RequestMapping("uploadPictureJumping")
	public String uploadPictureJumping(Model model) {
		int Pid=10;//查询概括类
		List<TbCategory> list=categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("uploadList", list);
		} else {
			model.addAttribute("message","该类别名称不存在");
		}
		return "admin/managepicture/upload";
	}
	
	/**
	 * @param model
	 * @param caName
	 * @param picture
	 * @return 学校风光的添加 ,图片轮播图的添加
	 */
	@RequestMapping("addSchoolScenery")
	public String addSchoolHistory(Model model,MultipartFile picture,TbResource tbResource) throws Exception {
		TbCategory tbCategory = categoryService.selectByPrimaryKey(tbResource.getCaId());
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String reContent = userService.uploadPicture(picture, path);
		if(tbCategory.getCaPid()==7) {
			TbResource tb = new TbResource(tbResource.getCaId(),tbResource.getCaName(),tbResource.getReTitle(),reContent);
			int i=resourceService.insert(tb);
			if(i!=0) {
				model.addAttribute("message", "添加成功");
			}else {
				model.addAttribute("message", "添加失败");
			}
		} else if(tbCategory.getCaPid()==10){
			TbResource tb = new TbResource(tbResource.getCaId(),tbCategory.getCaName(),reContent);
			int i=resourceService.insert(tb);
			if(i!=0) {
				model.addAttribute("message", "添加成功");
			}else {
				model.addAttribute("message", "添加失败");
			}
		}
		if(tbCategory.getCaPid()==10) {
			return this.uploadPictureJumping(model);
		}
		return this.addSceneryJumping(model);
	}
	
	/**
	 * @return 添加校园风光的跳转
	 */
	@RequestMapping("addSceneryJumping")
	public String addSceneryJumping(Model model) {
		List<TbCategory> item=categoryService.selectAll();
		for(TbCategory en:item) {
			if(en.getCaPid()==7) {
				model.addAttribute("tb",en);
			}
		}
		return "admin/general/addscenery";
	}

	/**
	 * @param model
	 * @param id
	 * @return 管理除了学校风光以外的所有
	 */
	@RequestMapping("manageGeneral")
	public String manage(Model model, Integer id) {
		TbResource tb = resourceService.selectByCaId(id);
		if (null != tb) {
			model.addAttribute("tbResource", tb);
		} else {
			model.addAttribute("message", "该名称不存在");
			return "admin/animation";
		}
		return "admin/general/managegeneral";
	}

	/**
	 * @param model
	 * @param id
	 * @return 删除学校风景
	 */
	@RequestMapping("deleteScenery")
	public String deleteScenery(Model model,Integer id) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
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
		return this.manageScenery(currentPage, model);
	}

	

	/**
	 * @param model
	 * @param picture
	 * @param tbResource
	 * @param id
	 * @return 修改图片
	 * @throws Exception
	 */
	@RequestMapping("updateSchoolScenery")
	public String updateSchoolHistory(Model model, MultipartFile picture, TbResource tbResource, Integer id)
			throws Exception {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if (!picture.getOriginalFilename().equals("")) {
			String path = session.getServletContext().getRealPath("static/uploadimg") + "/" + tb.getReContent();
			File files = new File(path);
			if (files.exists()) {
				files.delete();
			}
			String p = session.getServletContext().getRealPath("/static/uploadimg");
			String reContent = userService.uploadPicture(picture, p);
			tb.setReContent(reContent);
		}
		tb.setReTitle(tbResource.getReTitle());
		int i = resourceService.updateByPrimaryKey(tb);
		if (i != 0) {
			model.addAttribute("message", "修改成功");
		} else {
			model.addAttribute("message", "修改失败");
		}
		return this.manageScenery(currentPage, model);
	}

	/**
	 * @param model
	 * @return 添加类别类的跳转
	 */
	@RequestMapping("addGeneralJumping")
	public String addGeneralJumping(Model model) {
		int Pid=1;//查询概括类
		List<TbCategory> list=categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		} else {
//			model.addAttribute("message","该类别名称不存在");
			model.addAttribute("sign", 1);
		}
		return "admin/general/addgeneral";
	}

	/**
	 * @param model
	 * @param picture
	 * @param tbResource
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException 添加学校概括类除了校园风景
	 */
	@RequestMapping("addGeneral")
	public String addGeneral(Model model, @RequestParam(value = "picture", required = false) MultipartFile[] picture,
			TbResource tbResource) throws IllegalStateException, IOException {
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		TbResource tbresource = resourceService.selectByCaId(tbResource.getCaId());
		if (null != tbresource&&1==item.getCaPid()) {
			model.addAttribute("message", tbresource.getCaName() + "已存在");
			return this.addGeneralJumping(model);
		}
		if (null != item) {
			Date date = new Date();
			DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); //HH表示24小时制；  
	        String Date = dFormat.format(date); 
			TbResource tb = new TbResource(item.getCaId(),Date,item.getCaName(),tbResource.getReTitle(),tbResource.getReContent());
			resourceService.insert(tb);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别，添加失败");
		}
		return this.addGeneralJumping(model);
	}

	// 用于富文本编辑器的图片上传
	@RequestMapping("uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response) throws Exception {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String fileName = userService.uploadPicture(file, path);
		// 返回图片的URL地址
		response.getWriter().write("/middleschool/static/uploadimg/" + fileName);
	}
}
