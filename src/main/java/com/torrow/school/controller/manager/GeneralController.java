package com.torrow.school.controller.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
	 * @return 概括类的分页查看
	 */
	@RequestMapping("manageGeneral")
	public String manageGeneral(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, 1));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		return "admin/managegeneral";
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
	public String updateGeneral(Model model, int id, String reTitle, String reContent) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		TbResource record = new TbResource(tb.getReId(), tb.getCaId(), tb.getCaName(), reTitle, tb.getReIssuer(),
				tb.getReIssuingdate(), reContent);
		int i = resourceService.updateByPrimaryKey(record);
		if (i != 0) {
			model.addAttribute("message", "修改成功");
		} else {
			model.addAttribute("message", "修改失败");
		}
		return this.manageGeneral(currentPage, model);
	}

	/**
	 * @return 删除概括类
	 */
	@RequestMapping("deleteGeneral")
	public String deleteGeneral(Model model, int id) {
		int currentPage = (int) session.getAttribute("currentPage");
		int i = resourceService.deleteByPrimaryKey(id);
		if (i != 0) {
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "删除失败");
		}
		return this.manageGeneral(currentPage, model);
	}

	@RequestMapping("manageGeneralJumping")
	public String manageGeneralJumping() {
		return "admin/general";
	}

	/**
	 * @param model
	 * @return 查看学校简介
	 */
	@RequestMapping("checkSchoolIntroduction")
	public String schoolIntroduction(Model model) {
		List<TbResource> tbResource = resourceService.selectAll();
		for (TbResource item : tbResource) {
			if (item.getCaName().equals("学校简介")) {
				model.addAttribute("introduction", item);
			}
		}

		return "admin/general/checkschoolIntroduction";
	}

	/**
	 * @param model
	 * @return 对于资源类的添加跳转
	 */
	@RequestMapping("generalJumping")
	public String resourcejumping(Model model) {
		return "admin/addgeneral";
	}

	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 概括类的添加
	 */
	@RequestMapping("addGeneral")
	public String addGeneral(Model model, String reTitle, String reContent, String caName) {
		TbCategory item = categoryService.selectCaName(caName);
		if (!item.equals(null)) {
			TbResource record = new TbResource(item.getCaId(), caName, reTitle, reContent);
			resourceService.insert(record);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别,添加失败");
		}
		return "admin/addgeneral";
	}

	/**
	 * @param model
	 * @param caName
	 * @param picture
	 * @return 学校历史的添加
	 */
	@RequestMapping("addSchoolHistory")
	public String addSchoolHistory(Model model, String caName, MultipartFile picture) {
		log.info("111" + picture);
		TbCategory item = categoryService.selectCaName(caName);
		if (!item.equals(null)) {
			TbResource tb = new TbResource();
			tb.setCaId(item.getCaId());
			tb.setCaName(caName);
			if (picture != null) {
				try {
					String reTitle = this.uploadPicture(picture);
					tb.setReTitle(reTitle);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
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

	// 上传图片,返回文件名
	public String uploadPicture(MultipartFile picture) throws IllegalStateException, IOException {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String fileName = picture.getOriginalFilename();
		fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);// uuid+文件扩展名避免重名,中文名等问题
		File uploadFile = new File(path, fileName);
		picture.transferTo(uploadFile);
		return fileName;
	}

	@RequestMapping("addSchoolHonorJumping")
	public String addSchoolHonorJumping() {
		return "admin/general/addschoolhonor";
	}

	/**
	 * ajax上传图片，以json响应
	 * 
	 * @throws IllegalStateException
	 * 
	 * @throws @ResponseBody
	 *             返回json数据，将pojo数据转化为json
	 * @throws IOException
	 * 
	 * 			@RequestMapping("uploadPicture") public @ResponseBody Map<String,
	 *             Object> uploadPicture(MultipartFile picture) throws
	 *             IllegalStateException, IOException { String msg = "上传成功"; if
	 *             (picture == null) { msg = "上传失败，服务器繁忙"; } else { String path =
	 *             session.getServletContext().getRealPath("/static/uploadimg");
	 *             String fileName = UUID.randomUUID() +
	 *             picture.getOriginalFilename();// uuid避免重名 File uploadFile = new
	 *             File(path, fileName); picture.transferTo(uploadFile); } //
	 *             将map对象转换成json类型数据 Map<String, Object> map = new HashMap<String,
	 *             Object>(); map.put("msg", msg); return map; }
	 */
	@RequestMapping("addSchoolHonor")
	public String addSchoolHonor(Model model, @RequestParam(value = "file", required = false) MultipartFile[] file,
			TbResource tbResource) throws IllegalStateException, IOException {
		TbCategory item = categoryService.selectCaName(tbResource.getCaName());
		if (!item.equals(null)) {
			TbResource tb = new TbResource();
			tb.setCaId(item.getCaId());
			tb.setCaName(tbResource.getCaName());
			if(!tbResource.getReContent().equals(null)) {
				tb.setReContent(tbResource.getReContent());
			}
			for (MultipartFile mf : file) {
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
		return "admin/general/addschoolhonor";
	}
}
