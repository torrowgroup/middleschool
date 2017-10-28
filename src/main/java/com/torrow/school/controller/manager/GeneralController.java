package com.torrow.school.controller.manager;

import java.io.File;

import java.io.IOException;
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
 * @author 安李杰
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
	 * @return 管理概括类
	 */
	@RequestMapping("manageGeneral11")
	public String manageGeneral(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			String caName) {
		TbCategory record = new TbCategory();
		record.setCaName(caName);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		session.setAttribute("caName", caName);
		model.addAttribute("sign", 1);// 这个意在在查看和管理概括类界面时显示不同
		if (caName.equals("学校荣誉")) {
			return "admin/general/manageschoolhonor";
		} else if (caName.equals("学校简介")) {
			return "admin/general/manageschoolintroduction";
		} else if (caName.equals("领导机构")) {
			return "admin/general/manageauthorities";
		} else if (caName.equals("教学成果")) {
			return "admin/general/manageachievements";
		}
		return "admin/general/manageschoolhistory";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @param caName
	 * @return 查看概括类
	 */
	@RequestMapping("checkGeneral")
	public String checkGeneral(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			String caName) {
		TbCategory record = new TbCategory();
		record.setCaName(caName);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		session.setAttribute("caName", caName);
		if (caName.equals("学校荣誉")) {
			return "admin/general/manageschoolhonor";
		} else if (caName.equals("学校简介")) {
			return "admin/general/manageschoolintroduction";
		} else if (caName.equals("领导机构")) {
			return "admin/general/manageauthorities";
		} else if (caName.equals("教学成果")) {
			return "admin/general/manageachievements";
		}
		return "admin/general/manageschoolhistory";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 查看校园风光
	 */
	@RequestMapping("checkScenery")
	public String checkScenery(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		TbCategory record = new TbCategory();
		record.setCaName("校园风光");
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record));// 回显分页数据
		model.addAttribute("sign", 1);// 为了在查看管理校园风光时是同一个界面
		session.setAttribute("currentPage", currentPage);
		return "admin/general/managescenery";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 管理学校风光
	 */
	@RequestMapping("manageScenery")
	public String manageScenery(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		TbCategory record = new TbCategory();
		record.setCaName("校园风光");
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record));// 回显分页数据
		model.addAttribute("sign", 1);// 为了在查看管理校园风光时是同一个界面
		session.setAttribute("currentPage", currentPage);
		return "admin/general/managescenery";
	}

	/**
	 * @param model
	 * @param id
	 * @return 得到概括类的一条记录
	 */
	@RequestMapping("selectOnegeneral")
	public String selectOnegeneral(Model model, int id) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		model.addAttribute("general", tb);
		return "admin/general/updategeneral";
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
	public String updateGeneral(Model model, int id, TbResource tbResource) {
		int currentPage = (int) session.getAttribute("currentPage");
		String caName = (String) session.getAttribute("caName");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		TbResource record = new TbResource(tb.getReId(), tb.getCaId(), tb.getCaName(), tbResource.getReTitle(),
				tb.getReIssuer(), tb.getReIssuingdate(), tbResource.getReContent());
		int i = resourceService.updateByPrimaryKey(record);
		if (i != 0) {
			model.addAttribute("message", "修改成功");
		} else {
			model.addAttribute("message", "修改失败");
		}
		return this.manageGeneral(currentPage, model, caName);
	}

	/**
	 * @return 删除概括类
	 */
	@RequestMapping("deleteGeneral")
	public String deleteGeneral(Model model, int id) {
		int currentPage = (int) session.getAttribute("currentPage");
		String caName = (String) session.getAttribute("caName");
		int i = resourceService.deleteByPrimaryKey(id);
		if (i != 0) {
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "删除失败");
		}
		return this.manageGeneral(currentPage, model, caName);
	}

	@RequestMapping("manageGeneralJumping")
	public String manageGeneralJumping(Model model) {
		int Pid = 1;
		List<TbCategory> list = categoryService.queryByPid(Pid);
		model.addAttribute("categoryList", list);
		return "admin/general/index";
	}

	/**
	 * @param model
	 * @return 对于资源类的添加跳转
	 */
	@RequestMapping("generalJumping")
	public String resourcejumping() {

		return "admin/general/addintroduce";
	}

	@RequestMapping("generalJ")
	public String resourcej() {

		return "admin/general/addauthorities";
	}

	@RequestMapping("generalJump")
	public String resourcejump() {

		return "admin/general/addachievements";
	}

	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 概括类的添加
	 */
	/*
	 * @RequestMapping("addGeneral") public String addGeneral(Model model,
	 * TbResource tbResource) { TbCategory item =
	 * categoryService.selectCaName(tbResource.getCaName()); if (null != item) {
	 * TbResource record = new TbResource(item.getCaId(), tbResource.getCaName(),
	 * tbResource.getReTitle(), tbResource.getReContent());
	 * resourceService.insert(record); model.addAttribute("message", "添加成功"); } else
	 * { model.addAttribute("message", "不存在该类别,添加失败"); } if
	 * (tbResource.getCaName().equals("领导机构")) { return
	 * "admin/general/addauthorities"; } if (tbResource.getCaName().equals("教学成果"))
	 * { return "admin/general/addachievements"; } return
	 * "admin/general/addintroduce"; }
	 */
	/**
	 * @return 添加学校荣誉
	 */
	@RequestMapping("addAchievements")
	public String addAchievements(Model model, TbResource tbResource) {
		TbCategory item = categoryService.selectCaName(tbResource.getCaName());
		if (null != item) {
			TbResource record = new TbResource(item.getCaId(), tbResource.getCaName(), tbResource.getReIssuer(),
					tbResource.getReContent());
			resourceService.insert(record);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别,添加失败");
		}
		return "admin/general/addachievements";
	}

	/**
	 * @param model
	 * @param caName
	 * @param picture
	 * @return 学校历史的添加
	 */
	@RequestMapping("addSchoolHistory")
	public String addSchoolHistory(Model model, String caName, MultipartFile picture) throws Exception {
		TbCategory item = categoryService.selectCaName(caName);
		if (null != item) {
			TbResource tb = new TbResource();
			tb.setCaId(item.getCaId());
			tb.setCaName(caName);
			String path = session.getServletContext().getRealPath("/static/uploadimg");
			String reTitle = userService.uploadPicture(picture, path);
			tb.setReTitle(reTitle);
			resourceService.insert(tb);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别，添加失败");
		}
		return "admin/general/addschoolhistory";
	}

	@RequestMapping("addSchoolHistoryJumping")
	public String addSchoolHistoryJumping() {
		return "admin/general/addschoolhistory";
	}

	/**
	 * @return 添加学校荣誉的跳转
	 */
	@RequestMapping("addSchoolHonorJumping")
	public String addSchoolHonorJumping() {
		return "admin/general/addschoolhonor";
	}

	@RequestMapping("addSceneryJumping")
	public String addSceneryJumping() {
		return "admin/general/addscenery";
	}

	/**
	 * @param model
	 * @param file
	 * @param tbResource
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 *             添加学校荣誉
	 */
	@RequestMapping("addSchoolHonor")
	public String addSchoolHonor(Model model,
			@RequestParam(value = "picture", required = false) MultipartFile[] picture, TbResource tbResource)
			throws IllegalStateException, IOException {
		TbCategory item = categoryService.selectCaName(tbResource.getCaName());
		if (null != item) {
			TbResource tb = new TbResource();
			tb.setCaId(item.getCaId());
			tb.setCaName(tbResource.getCaName());
			if (!tbResource.getCaName().equals("校园风光")) {
				tb.setReContent(tbResource.getReContent());
			}
			for (MultipartFile mf : picture) {
				if (!mf.isEmpty()) {
					String path = session.getServletContext().getRealPath("/static/uploadimg");
					String fileName = mf.getOriginalFilename();
					fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);// uuid+文件扩展名避免重名,中文名等问题
					File uploadFile = new File(path, fileName);
					mf.transferTo(uploadFile);
					tb.setReTitle(fileName);
					resourceService.insert(tb);
				}
			}
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别，添加失败");
		}
		if (tbResource.getCaName().equals("校园风光")) {
			return "admin/general/addscenery";
		}
		return "admin/general/addschoolhonor";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 管理除了学校风光以外的所有
	 */
	@RequestMapping("manageGeneral")
	public String manage(Model model,Integer id) {
		TbResource tb = resourceService.selectByCaId(id);
		model.addAttribute("tbResource", tb);
		return "";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 删除学校风景
	 */
	@RequestMapping("deleteScenery")
	public String deleteScenery(Model model, Integer id) {
		int currentPage = (int) session.getAttribute("currentPage");
		String caName = (String) session.getAttribute("caName");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		String path = session.getServletContext().getRealPath("static/uploadimg") + "/" + tb.getReTitle();
		File files = new File(path);
		if (files.exists()) {
			files.delete();
		}
		String msg = "删除失败";
		if (resourceService.deleteByPrimaryKey(id) == 1) {
			msg = "删除成功";
		}
		model.addAttribute("message", msg);
		if (tb.getCaName().equals("建校历史") || tb.getCaName().equals("学校荣誉")) {
			return this.manageGeneral(currentPage, model, caName);
		}
		return this.manageScenery(currentPage, model);
	}

	/**
	 * @param model
	 * @param id
	 * @return 查询一条学校历史的记录
	 */
	@RequestMapping("selectOneHistory")
	public String selectOneHistory(Model model, Integer id) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		model.addAttribute("tbResource", tb);
		if ("学校荣誉".equals(tb.getCaName())) {
			return "admin/general/updateschoolhonor";
		}
		return "admin/general/updateschoolhistory";
	}

	/**
	 * @param model
	 * @param picture
	 * @param tbResource
	 * @param id
	 * @return 修改图片
	 * @throws Exception
	 */
	@RequestMapping("updateSchoolHistory")
	public String updateSchoolHistory(Model model, MultipartFile picture, TbResource tbResource, Integer id)
			throws Exception {
		int currentPage = (int) session.getAttribute("currentPage");
		String caName = (String) session.getAttribute("caName");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if ("学校荣誉".equals(tb.getCaName())) {
			tb.setReContent(tbResource.getReContent());
		}
		if (!picture.getOriginalFilename().equals("")) {
			String path = session.getServletContext().getRealPath("static/uploadimg") + "/" + tb.getReTitle();
			File files = new File(path);
			if (files.exists()) {
				files.delete();
			}
			String p = session.getServletContext().getRealPath("/static/uploadimg");
			String reTitle = userService.uploadPicture(picture, p);
			tb.setReTitle(reTitle);
		}
		int i = resourceService.updateByPrimaryKey(tb);
		if (i != 0) {
			model.addAttribute("message", "修改成功");
		} else {
			model.addAttribute("message", "修改失败");
		}
		if (tb.getCaName().equals("校园风光")) {
			return this.manageScenery(currentPage, model);
		}
		return this.manageGeneral(currentPage, model, caName);
	}

	/**
	 * @param model
	 * @return 添加类别类的跳转
	 */
	@RequestMapping("addGeneralJumping")
	public String addGeneralJumping(Model model) {
		int Pid = 1;
		List<TbCategory> list = categoryService.queryByPid(Pid);
		model.addAttribute("categoryList", list);
		return "admin/general/addgeneral";
	}

	@RequestMapping("addGeneral")
	public String addGeneral(Model model, @RequestParam(value = "picture", required = false) MultipartFile[] picture,
			TbResource tbResource) throws IllegalStateException, IOException {
		TbResource tbresource = resourceService.selectByCaId(tbResource.getCaId());
		if (null != tbresource) {
			model.addAttribute("message", tbresource.getCaName() + "已存在");
			return this.addGeneralJumping(model);
		}
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		if (null != item) {
			TbResource tb = new TbResource();
			tb.setCaId(item.getCaId());
			tb.setCaName(item.getCaName());
			tb.setReContent(tbResource.getReContent());
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
