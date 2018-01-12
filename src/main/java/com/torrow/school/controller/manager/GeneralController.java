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
		session.setAttribute("currentPage", currentPage);
		return "admin/general/managescenery";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 查询一条信息
	 */
	@RequestMapping("selectOneScenery")
	public String selectOneScenery(Model model,Integer id) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if(null!=tb) {
			model.addAttribute("tb",tb);
		}else {
			//返回提示页面
			model.addAttribute("message", "该校园风光不存在!");
			return "admin/empty";
		}
		return "admin/general/updateschoolscenery";
	}
	
	/**
	 * @return 轮播图的跳转
	 */
	@RequestMapping("uploadCarouselJumping")
	public String uploadCarouselJumping() {
		return "admin/managepicture/upload";
	}

	
	/**
	 * @param model
	 * @param tbResource
	 * @return 上传轮播图和校徽
	 * @throws Exception 
	 */
	@RequestMapping("uploadCarousel")
	public String uploadCarousel(Model model,TbResource tbResource,MultipartFile picture) throws Exception {
		List<TbResource> list=new ArrayList<TbResource>();
		List<TbResource> one=new ArrayList<TbResource>();
		List<TbResource> t = resourceService.selectAll();
			for(TbResource en:t) {
				if("图片".equals(en.getCaName())) {
					if("首页轮播".equals(en.getReTitle())&&tbResource.getReTitle().equals("首页轮播")) {
						model.addAttribute("message", "首页轮播最多有3幅");
						list.add(en);
					}else if(!tbResource.getReTitle().equals("首页轮播")&&tbResource.getReTitle().equals(en.getReTitle())){
						model.addAttribute("message",tbResource.getReTitle()+"最多有一幅轮播图片");
						one.add(en);
					}
				}
			}
		//判断图片是否是3个
		if (list.size() > 2||one.size()==1) {
			return "admin/managepicture/upload";
		}
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String reContent = resourceService.uploadPicture(picture, path);
		TbResource record = new TbResource("图片",tbResource.getReTitle(),
				reContent);
		record.setCaId(0);
		int i = resourceService.insert(record);
		if (i != 0) {
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "添加失败");
		}
		return "admin/managepicture/upload";
	}
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 这个分页是为了查看轮播图和校徽等图片信息仅此
	 */
	@RequestMapping("manageCarousel")
	public String manageCarousel(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,Model model) {
		String inquiry="图片";
		model.addAttribute("pagemsg", resourceService.findingByPage(currentPage, inquiry, 3));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		return "admin/managepicture/managepicture";
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
		String reContent = resourceService.uploadPicture(picture, path);
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
		return this.addSceneryJumping(model);
	}
	
	/**
	 * @return 添加校园风光的跳转
	 */
	@RequestMapping("addSceneryJumping")
	public String addSceneryJumping(Model model) {
		List<TbCategory> item=categoryService.selectAll();
		boolean enough=false;
		for(TbCategory en:item) {
				if(en.getCaPid()==7) {
					model.addAttribute("tb",en);
					enough=true;
				}
		}
		if(enough==false) {
			model.addAttribute("message","该类别名称不存在请先去类别类中添加");
			return "admin/empty";
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
			model.addAttribute("message", "您还没有添加这方面的内容,请先在概括类中添加该项内容");
			return "admin/empty";
		}
		return "admin/general/managegeneral";
	}
	
	/**
	 * @return 删除概括类
	 */
	@RequestMapping("deleteGeneral")
	public String deleteGeneral(Model model, int id) {
		int l = resourceService.deleteByPrimaryKey(id);
		if (l != 0) {
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "删除失败");
		}
		return "admin/empty";
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
			//这是对于图片信息（轮播图）的删除
			if(tb.getCaName().equals("图片")) {
				return this.manageCarousel(currentPage, model);
			}
		}else {
			model.addAttribute("message", "这条数据不存在!");
		}
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
			String reContent = resourceService.uploadPicture(picture, p);
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
		String fileName = resourceService.uploadPicture(file, path);
		// 返回图片的URL地址
		response.getWriter().write(session.getServletContext().getContextPath()+"/static/uploadimg/" + fileName);
	}
}
