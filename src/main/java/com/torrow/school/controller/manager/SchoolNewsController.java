package com.torrow.school.controller.manager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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
 * @author 安李杰
 *	这里边有校园新闻和通知公告、教育教研类的管理也在这一块、校园文学也在这一块
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
	 * @return 添加新闻类的跳转
	 */
	@RequestMapping("addNewsJumping")
	public String newsJumping(Model model) {
		int Pid=2;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		} else {
			model.addAttribute("sign",1);
		}
		return "admin/schoolnews/addschoolnews";
	}
	
	/**
	 * @param model
	 * @return 对于学生管理和教师成长的上传
	 */
	@RequestMapping("uploadJumpping")
	public String uploadJumpping(Model model){
		int Pid=9;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("uploadList", list);
		}else {
			model.addAttribute("sign",1);
		}
		return "admin/schoolnews/uploadfile";
	}
	/**
	 * @param model
	 * @return 教育教研类的跳转
	 */
	@RequestMapping("educationJumpping")
	public String educationJumpping(Model model) {
		int Pid=3;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("uploadList", list);
		}else {
			model.addAttribute("sign",1);
		}
		return "educationoffice/uploadfile";
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
		TbResource retitle = resourceService.selectByReTitle(tbResource.getReTitle());
		if(null!=retitle) {
			model.addAttribute("message", "该名称已存在，添加失败");
			return this.newsJumping(model);
		}
		Date date = new Date();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); //HH表示24小时制；  
        String Date = dFormat.format(date);
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		if (null != item) {
			TbResource record = new TbResource(item.getCaId(), Date, item.getCaName(), tbResource.getReTitle(),
					tbResource.getReContent());
			int i=resourceService.insert(record);
			if(i!=0) {
				model.addAttribute("message", "添加成功");
			}else {
				model.addAttribute("message", "添加失败");
			}
		} else {
			model.addAttribute("message", "添加失败,不存在该类别");
		}
		if(item.getCaPid()==3||item.getCaPid()==5) {
			return this.sendPrivateNoticeJumping(model);
		}
		if(item.getCaPid()==6) {
			return this.addNoticeJumping(model);
		}
		return this.newsJumping(model);
	}

	/**
	 * @param currentPage
	 * @param model
	 * @return 校园新闻类/学校公告的管理
	 */
	@RequestMapping("manageSchoolNews")
	public String manageSchoolNews(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer id) {
		TbCategory record = new TbCategory();
//		record.setCaPid(2);
		record.setCaId(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record,10));// 回显分页数据
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
	public String manageUpload(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer id) {
		TbCategory record = new TbCategory();
//		record.setCaPid(2);
		record.setCaId(id);
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record,10));// 回显分页数据
		session.setAttribute("currentPage", currentPage);
		model.addAttribute("sign", 1);
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
			return "admin/schoolnews/index";
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
			int i=resourceService.updateByPrimaryKey(tb);
			if(i!=0) {
				model.addAttribute("message", "保存成功");
			}else {
				model.addAttribute("message", "保存失败");
			}
		} 
		return this.manageJumping(model);
	}

	/**
	 * @param model
	 * @param
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
		return this.manageSchoolNews(currentPage, model,1);
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
		if (null != item) {
			String path = session.getServletContext().getRealPath("/static/uploadimg");
			String reContent = userService.uploadPicture(file, path);
			TbResource resource = resourceService.selectByReContent(reContent);
			if(null==resource) {
				model.addAttribute("message","该文件已存在,上传失败");
				return this.uploadJumpping(model);
			}
			TbResource tb = new TbResource(item.getCaId(),item.getCaName(),file.getOriginalFilename(),reContent);
			resourceService.insert(tb);
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "不存在该类别，添加失败");
		}
		return this.uploadJumpping(model);
	}
	/**
	 * @param model
	 * @return 在管理新闻时从数据库类别类查出来的新闻名称
	 */
	@RequestMapping("manageJumping")
	public String manageJumping(Model model) {
		int Pid = 2;
		int id=9;
		List<TbCategory> list = categoryService.queryByPid(Pid);
		List<TbCategory> item = categoryService.queryByPid(id);
		if(!list.isEmpty()||!item.isEmpty()) {
			model.addAttribute("categoryList", list);
			model.addAttribute("itemList",item);
		} else {
			model.addAttribute("message","该名称不存在");
		}
		return "admin/schoolnews/index";
	}
	
	
	// 用于富文本编辑器的图片上传
	@RequestMapping("uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response) throws Exception {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String fileName = userService.uploadPicture(file, path);
		// 返回图片的URL地址
		response.getWriter().write("/middleschool/static/uploadimg/" + fileName);
	}
	
//	---------------------------------------------------以下写的是通知公告类
	/**
	 * @param model
	 * @return 添加学校公告类的跳转
	 */
	@RequestMapping("addNoticeJumping")
	public String addNoticeJumping(Model model) {
		int Pid=6;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		} else {
			model.addAttribute("sign",1);
		}
		return "admin/schoolnews/addschoolnews";
	}
	
	/**
	 * @param model
	 * @return 
	 */
	@RequestMapping("sendPrivateNoticeJumping")
	public String sendPrivateNoticeJumping(Model model) {
		int Pid=3;
		int p=5;
		List<TbCategory> list=categoryService.queryByPid(Pid);
		List<TbCategory> item=categoryService.queryByPid(p);
		if(!list.isEmpty()||!item.isEmpty()) {
			model.addAttribute("categoryList", list);
			model.addAttribute("itemList",item);
		} else {
			model.addAttribute("sign",1);
		}
		return "admin/notice/sendprivatenotice";
	}
	
	/**
	 * @param model
	 * @return 进入管理通知公告类的跳转
	 */
	@RequestMapping("noticeJumping")
	public String noticeJumping(Model model) {
		int Pid = 6;
		List<TbCategory> list = categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		}else {
			model.addAttribute("message","该类别名称不存在");
		}
		return "admin/notice/index";
	}
//	----------------------------------------------------------
	/**
	 * @param model
	 * @return 校园文学类的跳转
	 */
	@RequestMapping("literatureJumping")
	public String literatureJumping(Model model) {
		int Pid = 12;
		List<TbCategory> list = categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		}else {
			model.addAttribute("message","该类别名称不存在");
		}
		return "admin/schoolliterature/uploadfile";
	}
	
	/**
	 * @param model
	 * @return 管理校园文学
	 */
	@RequestMapping("manageliteratureJumping")
	public String manageliteratureJumping(Model model) {
		int Pid = 12;
		List<TbCategory> list = categoryService.queryByPid(Pid);
		if(!list.isEmpty()) {
			model.addAttribute("categoryList", list);
		}else {
			model.addAttribute("message","该类别名称不存在");
		}
		return "admin/schoolliterature/index";
	}
	
	/**  
     * 文件下载功能  
     * @param request  
     * @param response  
     * @throws Exception  
     */  
    @RequestMapping("/down")  
    public void down(HttpServletRequest request,HttpServletResponse response,int id) throws Exception{  
    	TbResource tb = resourceService.selectByPrimaryKey(id);
    	//模拟文件，myfile.txt为需要下载的文件  
        String fileName = request.getSession().getServletContext().getRealPath("/static/uploadimg")+"/" + tb.getReContent(); 
        //获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));  
        //假如以中文名下载的话  
        String filename = "下载文件.txt";  
        //转码，免得文件名中文乱码  
        filename = URLEncoder.encode(filename,"UTF-8");  
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();  
    }  
}
