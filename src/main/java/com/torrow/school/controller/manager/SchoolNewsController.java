package com.torrow.school.controller.manager;

import java.io.File;



import java.io.UnsupportedEncodingException;
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
	 * @param model父ID
	 * @return 添加校园新闻2、上传9（学生管理、教师成长）、 教研组上的上传3、资源下载类的上传11、校园文学类的上传12、学校公告类的添加6
	 */
	@RequestMapping("addNewsJumping")
	public String addNewsJumping(Model model, int Pid) {
		//这个方法是为了返回类别数据在添加页面
		categoryService.addBySelectPid(model, Pid);
		if (Pid == 9 || Pid == 3 || Pid == 12) {
			return "admin/schoolnews/uploadfile";
		}else if(Pid == 11){
			return "admin/download/upload";
		}else if(Pid == 6) {
			return "admin/notice/addnotice";
		}
		return "admin/schoolnews/addschoolnews";
	}

	/**
	 * @param model
	 * @param id
	 * @return 删除文件的
	 */
	@RequestMapping("deleteFile")
	public String deleteFile(Model model, Integer id) {
		//从session中取得分页数
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if (null != tb) {
			//去服务器文件目录下看该文件是否存在，存在就删
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
			//返回提示页面
			model.addAttribute("message", "这个文件不存在");
			return "admin/empty";
		}
		//这个是为了封装ID去管理页面查找数据
		TbCategory record = new TbCategory();
		record.setCaId(tb.getCaId());
//		if (session.getAttribute("caName") != null) {
//			String caName = (String) session.getAttribute("caName");
//			record.setCaName(caName);
//		}
		return this.manageObject(currentPage, model, record);
	}

	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 添加学校新闻（父ID为2）和添加学校公告（父ID为6）
	 *
	 */
	@RequestMapping("addSchoolNews")
	public String addSchoolNews(Model model, TbResource tbResource) {
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		//这个方法是为了判断标题是否重复
	    if(resourceService.getNumber(tbResource, model, 1)==1) {
	    	model.addAttribute("message", "该标题已存在，添加失败");
	    	if (item.getCaPid() == 2) {
				return this.addNewsJumping(model, 2);
			} else {
				return this.addNewsJumping(model, 6);
			}
	    }
		if (null != item) {
			TbResource record = new TbResource(item.getCaId(), resourceService.Date(), item.getCaName(), tbResource.getReTitle(),
					tbResource.getReContent());
			 //从用户登录信息中取出用户名在添加公告时用
			 if(item.getCaPid()==6) {
				 TbUser tbUser=(TbUser)session.getAttribute("admin");
				 record.setReIssuer(tbUser.getUsName());
			 }
			 record.setSpare("否");
			 //把数据存数据库			 
			int i = resourceService.insert(record);
			if (i != 0) {
				model.addAttribute("message", "添加成功");
			} else {
				model.addAttribute("message", "添加失败");
			}
		} else {
			model.addAttribute("message", "添加失败,不存在该类别");
		}
		//返回添加公告
		if (item.getCaPid() == 6) {
			return this.addNewsJumping(model, 6);
		}
		//返回添加新闻
		return this.addNewsJumping(model, 2);
	}
	
	/**
	 * @param model
	 * @param id
	 * @param tbCategory
	 * @return 置顶和取消置顶的操作,sign==1,代表置顶状态,sign==0,代表非置顶状态
	 */
	@RequestMapping("stick")
	public String stick(Model model,int id,TbCategory tbCategory,int sign) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		if(sign==1&&tb.getSpare().equals("是")) {
			tb.setSpare("否");
			model.addAttribute("message", "取消置顶成功");
		}else if(sign==0&&tb.getSpare().equals("否")){
			tb.setSpare("是");
			model.addAttribute("message", "置顶成功");
		} else {
			model.addAttribute("message","状态不可变!");
			return manageObject(currentPage,model,tbCategory);
		}
		resourceService.updateByPrimaryKey(tb);
		return manageObject(currentPage,model,tbCategory);
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
			//这个方法是为了当这条数据不存在时返回一个提示页面
			model.addAttribute("message", "该名称不存在");
			return "admin/empty";
		}
		TbCategory tbCategory = categoryService.selectByPrimaryKey(tb.getCaId());
		if (tbCategory.getCaPid() == 6) {
			// 这步是为了在数据没时提示语句
			model.addAttribute("sign", 1);
		}
		return "admin/schoolnews/managenews";
	}


	/**
	 * @param model
	 * @param
	 * @return 删除学校新闻和学校公告
	 */
	@RequestMapping("deleteNews")
	public String deleteNews(Model model, int id) {
		int currentPage = (int) session.getAttribute("currentPage");
		TbResource tb = resourceService.selectByPrimaryKey(id);
		// 这是为了返回那条信息的分页位置
		TbCategory record = new TbCategory();
		if (null != tb) {
			int i = resourceService.deleteByPrimaryKey(id);
			if (i != 0) {
				model.addAttribute("message", "删除成功");
			} else {
				model.addAttribute("message", "删除失败");
			}
			TbCategory tbCategory = categoryService.selectByPrimaryKey(tb.getCaId());
			if(tbCategory.getCaPid()==6) {
				record.setCaPid(6);
			}else {
				record.setCaId(tb.getCaId());
			}
		} else {
			//这个为了返回提示页面
			model.addAttribute("message", "这条新闻不存在");
			return "admin/empty";
		}
		return this.manageObject(currentPage, model, record);
	}
	
	/**
	 * @param model 校园新闻类2/上传9/教育教研的管理3
	 * @return 管理资源下载11和通知公告6的的管理
	 * 之所以把参数改为对象是为了少传参数
	 */
	@RequestMapping("manageObject")
	public String manageObject(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model,
			TbCategory tbCategory) {
		TbCategory record = new TbCategory();
		//当以父ID来进行管理这些分页数据时用的主要有公告6，资源下载11，图片10
		if(null!=tbCategory.getCaPid()) {
			record.setCaPid(tbCategory.getCaPid());
			model.addAttribute("zid", tbCategory.getCaPid());
			session.setAttribute("Pid", tbCategory.getCaPid());
		}
		//对于以caId来进行查看这些分页数据
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
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 7));// 回显分页10条数据
		session.setAttribute("currentPage", currentPage);
		if(null!=tbCategory.getCaPid()) {
			if (tbCategory.getCaPid() == 6) {
				return "admin/notice/managenotice";
			} 
		}
		if(null!=tbCategory.getCaId()) {
			TbCategory t = categoryService.selectByPrimaryKey(tbCategory.getCaId());
			if (t.getCaPid() == 3 || t.getCaPid() == 9 || t.getCaPid() == 12) {
				return "admin/schoolnews/manageupload";
			}else if(t.getCaPid()==2) {
				return "admin/schoolnews/manageschoolnews";
			}
		}
		return "admin/download/manageupload";
	}

	/**
	 * @param tbResource
	 * @param picture
	 * @param model
	 * @return 上传的操作
	 * 有教研组的上传父ID为3，资源下载上传11，上传类的上传（学生管理和教师成长）9
	 * @throws Exception
	 */
	@RequestMapping("upload")
	public String upload(TbResource tbResource, MultipartFile file, Model model) throws Exception {
		TbCategory item = categoryService.selectByPrimaryKey(tbResource.getCaId());
		//这个是为了判断文件名在指定的父id下是否重,重的话跳转到上传页面重新上传
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
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String reContent = resourceService.uploadFile(file, path);
		//从session中获得用户名作为文件的上传名
		TbUser tbUser=(TbUser)session.getAttribute("admin");
		TbResource tb = new TbResource(item.getCaId(),tbUser.getUsName(), resourceService.Date(), item.getCaName(), file.getOriginalFilename(), reContent);
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
		String projectPath = session.getServletContext().getContextPath();
		// 返回图片的URL地址
		response.getWriter().write(projectPath+"/static/uploadimg/" + fileName);
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
		// 根据资源类的对应ID在数据库中获取相应数据，从而找到服务器的文件
		resourceService.down(request, response, id);
	}
}
