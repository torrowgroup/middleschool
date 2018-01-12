package com.torrow.school.controller.education;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	 * @return 教研组的上传  张金高改
	 */
	@RequestMapping("uploadEducation")
	public String uploadEducation(Model model) {
		TbUser tbUser=(TbUser)session.getAttribute("teacher");
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
		if(!enough) {
			model.addAttribute("message","你没有权利上传教研文件");
		}
		return "educationoffice/uploadfile";
	}

	/**
	 * @param model 到达上传校园文学界面 张金高
	 * @return
	 */
	@RequestMapping("toUploadLiterature")
	public ModelAndView toUploadLiterature(Model model) {
		int pid = 12; //校园文学
		List<TbCategory> list = categoryService.queryByPid(pid);
		model.addAttribute("literatureList", list);
		return new ModelAndView("educationoffice/uploadliterature");
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
		model.addAttribute("pagemsg", resourceService.findingByPaging(currentPage, record, 7));// 回显分页10条数据
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
		TbUser tbUser=(TbUser)session.getAttribute("teacher");
		TbResource tb = new TbResource(item.getCaId(), tbUser.getUsName(),Date, item.getCaName(), file.getOriginalFilename(), reContent);
		resourceService.insert(tb);
		model.addAttribute("message", "添加成功");
		return this.uploadEducation(model);
	}
	
	/**
	 * @param caId	上传的文学类别
	 * @param file 上传文件
	 * @throws Exception
	 *  上传校园文学文件 张金高
	 */
	@RequestMapping("uploadLiterature")
	public ModelAndView uploadLiterature(int caId,MultipartFile file,Model model) throws Exception{
		TbCategory category = categoryService.selectByPrimaryKey(caId);
		if(category!=null){
			List<TbResource> resource = resourceService.selectAll();
			for (TbResource en : resource) {
				if (en.getCaId() == category.getCaId()) {
					if (file.getOriginalFilename().equals(en.getReContent())) {
						model.addAttribute("message", "该文件已存在,上传失败");
							return this.toUploadLiterature(model);
					}
				}
			}
			String path = session.getServletContext().getRealPath("/static/uploadimg");
			String reContent = resourceService.uploadFile(file, path);
			DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd"); // HH表示24小时制；
			String Date = dFormat.format(new Date());
			TbUser user = (TbUser) session.getAttribute("teacher");
			TbResource tb = new TbResource(caId,user.getUsName(), Date, category.getCaName(), file.getOriginalFilename(), reContent);
			int boo = resourceService.insert(tb);
			if(boo==1){
				model.addAttribute("message", "添加成功");				
			} else {
				model.addAttribute("message", "添加失败");
			}
		}
		return this.toUploadLiterature(model);
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
	
	//以下都为张金高
	
	/**
	 * @return个人中心
	 */
	@RequestMapping("viewMe")
	public ModelAndView viewMe(){
		return new ModelAndView("educationoffice/updateme");
	}
	
	/**
	 * @param user
	 * @param picture
	 *  修改个人资料
	 */
	@RequestMapping("updateMe")
	public ModelAndView updateMe(TbUser user,MultipartFile picture,Model model) throws Exception{
		TbUser me = (TbUser)session.getAttribute("teacher");
		if(me!=null){
			user.setUsId(me.getUsId());//封装前台此界面不能修改的项
			user.setCaId(me.getCaId());
			user.setCaName(me.getCaName());
			user.setUsPassword(me.getUsPassword());
			user.setUsPicture(me.getUsPicture());
			if(!picture.getOriginalFilename().equals("")){//如果用户上传了图片，则换掉原来的图片
				String path = session.getServletContext().getRealPath("/static/uploadimg");
				File file = new File(path+"/"+me.getUsPicture());
				if(file.exists()){	//删掉不用的图片
					file.delete();
				}
				String fileName = userService.uploadPicture(picture, path);
				user.setUsPicture(fileName);
			}
			int boo = userService.updateByPrimaryKey(user);
			if(boo==1){
				model.addAttribute("message", "修改完成");
				session.setAttribute("teacher", user);
			} else {
				model.addAttribute("message", "修改失败");
			}
		}
		return new ModelAndView("educationoffice/updateme");
	}
	
	/**
	 * @return到达修改密码界面
	 */
	@RequestMapping("toUpdatePsw")
	public ModelAndView tpUpdatePsw(){
		return new ModelAndView("educationoffice/updatepsw");
	}
	
	/**
	 * @param oldPsw 旧密码
	 * @param psw 新密码
	 * @return 修改密码
	 */
	@RequestMapping("updatePsw")
	public ModelAndView updatePsw(String oldPsw,String password,Model model){
		TbUser user = (TbUser) session.getAttribute("teacher");
		String message = "修改失败,密码输入错误";
		if(user!=null&&user.getUsPassword().equals(oldPsw)){
			if(oldPsw.equals(password)){
				message="修改失败，旧密码和新密码相同";
			} else { 
				user.setUsPassword(password);
				int boo = userService.updateByPrimaryKey(user);
				if(boo==1){
					message = "修改成功";
					session.setAttribute("teacher", user);
				} else {
					message = "修改失败";
				}
			}
		}
		model.addAttribute("message", message);
		return new ModelAndView("educationoffice/updatepsw");
	}
	
	/**
	 * @return 到达上传资源界面
	 */
	@RequestMapping("toUploadResource")
	public ModelAndView toAddResource(){
		return new ModelAndView("educationoffice/uploadresource");
	}
	
	/**
	 * @param file
	 * @param model
	 * @return
	 * 		Integer caId,String reIssuer, String reIssuingdate,String caName, String reTitle,
			String reContent
	 * @throws Exception 上传资源
	 */
	@RequestMapping("uploadResource")
	public ModelAndView uploadResource(MultipartFile file,Model model) throws Exception{
		int pid = 11;
		TbCategory category = categoryService.queryByPid(pid).get(0);
		String message = this.uploadFile(category, file, model);
		if(!message.equals("文件已存在，上传失败")){
			TbUser user = (TbUser) session.getAttribute("teacher");
			String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			TbResource resource = new TbResource(category.getCaId(),user.getUsName(),dateString,category.getCaName(),message,message);
			int boo = resourceService.insert(resource);
			message = "上传失败";
			if(boo==1){
				message = "上传成功";				
			}
		}
		model.addAttribute("message", message);
		return new ModelAndView("educationoffice/uploadresource");
	}
	
	//如果可以上传返回上传后文件地址，文件已经存在则返回“已存在”
	public String uploadFile(TbCategory category,MultipartFile file,Model model) throws Exception{
		String message = "已存在";
		List<TbResource> resource = resourceService.selectAll();
		for (TbResource en : resource) {
			if (en.getCaId() == category.getCaId()) {
				if (file.getOriginalFilename().equals(en.getReContent())) {
					message = "文件已存在,上传失败";
					return message; 
				}
			}
		}
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		message = resourceService.uploadFile(file, path);
		return message;
	}
	// 验证用户邮箱是否被添加过
		@RequestMapping("testEmail")
		public @ResponseBody Map<String, Object> testEmail(String email) {
			String msg = "1";
			List<TbUser> lists = userService.selectAll();
			for (int i = 0; i < lists.size(); i++) {
				if (lists.get(i).getUsEmail().equals(email)) {
					msg = "0";
					break;
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("msg", msg);
			return map;
		}
}
