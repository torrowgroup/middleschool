package com.torrow.school.controller.manager;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;

@Controller
@RequestMapping("/news")
public class SchoolNewsController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping("addNewsJumping")
	public String newsJumping(Model model) {
		int Pid=2;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		model.addAttribute("categoryList", list);
		return "admin/schoolnews/addschoolnews";
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
		Date date=new Date();
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		if (null != item) {
			TbResource record = new TbResource(item.getCaId(), date, tbResource.getCaName(), tbResource.getReTitle(),
					tbResource.getReContent());
			resourceService.insert(record);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "添加失败,不存在该类别");
		}
		return "admin/schoolnews/addschoolnews";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 校园新闻类的管理
	 */
	@RequestMapping("manageSchoolNews")
	public String manageSchoolNews(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model) {
		TbCategory record = new TbCategory();
		record.setCaPid(2);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("sign", 1);
		return "admin/schoolnews/manageschoolnews";
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 查看新闻类别
	 */
	@RequestMapping("checkSchoolNews")
	public String checkSchoolNewsJumping(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model) {
		TbCategory record = new TbCategory();
		record.setCaPid(2);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		return "admin/schoolnews/manageschoolnews";
	}

	/**
	 * @param id
	 * @param model
	 * @return 查询一条新闻
	 */
	@RequestMapping("selectOneNews")
	public String selectOneNews(int id, Model model) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		model.addAttribute("news", tb);
		return "admin/schoolnews/updateschoolnews";
	}

	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @return 修改学校新闻
	 */
	@RequestMapping("updateSchoolNews")
	public String updateSchoolNews(Model model, int id, TbResource tbResource) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		TbResource record = new TbResource(tb.getReId(), tb.getCaId(), tb.getCaName(), tbResource.getReTitle(),
				tb.getReIssuer(), tb.getReIssuingdate(), tbResource.getReContent());
		int i = resourceService.updateByPrimaryKey(record);
		if (i != 0) {
			model.addAttribute("message", "修改成功");
		} else {
			model.addAttribute("message", "修改失败");
		}
		return this.manageSchoolNews(currentPage, model);
	}

	/**
	 * @param model
	 * @param id
	 * @return 删除学校新闻
	 */
	@RequestMapping("deleteNews")
	public String deleteNews(Model model, int id) {
		int currentPage = (int) session.getAttribute("currentPage");
		int i = resourceService.deleteByPrimaryKey(id);
		if (i != 0) {
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "删除失败");
		}
		return this.manageSchoolNews(currentPage, model);
	}

	/**
	 * @return 上传的跳转
	 */
	@RequestMapping("uploadJumping")
	public String uploadJumping() {

		return "admin/schoolnews/uploadmanage";
	}

	/**
	 * @param tbResource
	 * @param picture
	 * @param model
	 * @return 上传的操作
	 * @throws Exception
	 */
	@RequestMapping("upload")
	public String upload(TbResource tbResource, MultipartFile picture, Model model) throws Exception {
		TbCategory item = categoryService.selectCaName(tbResource.getCaName());
		if (null != item) {
			TbResource tb = new TbResource();
			tb.setCaId(item.getCaId());
			tb.setCaName(tbResource.getCaName());

			String path = session.getServletContext().getRealPath("/static/uploadimg");
			String reTitle = userService.uploadPicture(picture, path);
			tb.setReTitle(reTitle);
			resourceService.insert(tb);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别，添加失败");
		}
		return "admin/schoolnews/uploadmanage";
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
