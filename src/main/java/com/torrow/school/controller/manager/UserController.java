package com.torrow.school.controller.manager;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PageBean;

/**
 * @author 张金高
 *
 * @2017年10月6日下午5:33:50
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 查找所有用户，分页
	@RequestMapping("manageUser")
	public String manageUser(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, String inquiry, Model model) throws UnsupportedEncodingException {
		if(inquiry!=null){
			inquiry = new String(inquiry.getBytes("ISO-8859-1"), "UTF-8");
		}
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);//教育教研组
		pidList.add(4);//用户
		List<TbCategory> categoryList = categoryService.selectByPid(pidList);
		model.addAttribute("categoryList",categoryList);
		PageBean<TbUser> pageBean = userService.findPageSplit(categoryList,currentPage,inquiry, 5);
		model.addAttribute("pagemsg", pageBean);
		model.addAttribute("inquiry", inquiry);
		return "admin/user/manageuser";
	}

	// 到达添加用户界面
	@RequestMapping("toAddUser")
	public String toAddUser(Model model) {
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);// 将机构部3，用户4, 放进集合中
		pidList.add(4);
		List<TbCategory> list = categoryService.selectByPid(pidList);
		model.addAttribute("categoryList", list);
		return "admin/user/adduser";
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

	// 添加用户界面
	@RequestMapping("addUser")
	public String addUser(TbUser tbUser, MultipartFile picture, Model model) throws Exception {
		TbCategory category = categoryService.selectByPrimaryKey(tbUser.getCaId());
		tbUser.setCaName(category.getCaName());
		if (picture != null) {
			String path = session.getServletContext().getRealPath("/static/uploadimg");
			String usPicture = userService.uploadPicture(picture, path);
			tbUser.setUsPicture(usPicture);
		}
		TbUser userDb = userService.selectByEmail(tbUser.getUsEmail());
		String msg = "添加失败，用户已存在";
		if(userDb==null){
			int boo = userService.addUser(tbUser);
			if (boo == 1) {
				msg = "添加成功";
			} else {
				msg = "添加失败";
			}			
		}
		model.addAttribute("message", msg);
		return this.toAddUser(model);
	}

	// 到达修改用户界面
	@RequestMapping("toUpdateUser")
	public String toUpdateUser(int id,int page, Model model) {
		TbUser sessionUser = (TbUser)session.getAttribute("admin");
		if (sessionUser!=null&&sessionUser.getUsId()==id) {//如果修改的是自己，到达个人中心
			return "admin/user/viewme";
		}
		TbUser tbUser = userService.selectById(id);
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);// 将机构部3，用户4放进集合中
		pidList.add(4);
		List<TbCategory> list = categoryService.selectByPid(pidList);// 得到所有身份记录
		model.addAttribute("categoryList", list);
		model.addAttribute("user", tbUser);
		model.addAttribute("page", page);//得到当前页数并返回前台
		return "admin/user/updateuser";
	}

	//修改用户信息
	@RequestMapping("updateUser")
	public String updateUser(TbUser user,int page,MultipartFile picture,Model model) throws Exception{
		TbUser userBoo = userService.selectById(user.getUsId());
		if(userBoo==null){											//如果用户不存在，返回
			model.addAttribute("message", "用户不存在，修改失败");
		} else {
			TbCategory category = categoryService.selectByPrimaryKey(user.getCaId());
			user.setCaName(category.getCaName());//得到用户身份名称
			if(!picture.getOriginalFilename().equals("")){//如果用户上传了图片，则换掉原来的图片
				TbUser userData = userService.selectById(user.getUsId());
				String path = session.getServletContext().getRealPath("/static/uploadimg");
				File file = new File(path+"/"+userData.getUsPicture());
				if(file.exists()){	//删掉不用的图片
					file.delete();
				}
				String fileName = userService.uploadPicture(picture, path);
				user.setUsPicture(fileName);
			}
			int boo = userService.updateByPrimaryKey(user);
			if(boo==1){
				model.addAttribute("message", "修改成功");
			} else {
				model.addAttribute("message", "修改失败");
			}
		}
		return this.manageUser(page,null, model);//返回管理用户当前页面
	}
	// 删除用户
	@RequestMapping("deleteUser")
	public String deleteUser(int id, int page,Model model) throws UnsupportedEncodingException {
		TbUser tbUser = userService.selectById(id);
		String msg = "该用户已被删除";
		if(tbUser!=null) {
			TbUser admin = (TbUser)session.getAttribute("admin");
			if(admin!=null&&admin.getUsId()==tbUser.getUsId()){
				msg = "不能删除自己";
			} else {
				msg = "删除失败";
				String path = session.getServletContext().getRealPath("static/uploadimg") + "/" + tbUser.getUsPicture();
				File files = new File(path);
				if (files.exists()) {
					files.delete();
				}
				if (userService.deleteById(id) == 1) {
					msg = "删除成功";
				}
			}
		}
		model.addAttribute("message", msg);
		return this.manageUser(page,null, model);
	}
	
	/**
	 * @param model
	 * @return
	 * 查看个人资料
	 */
	@RequestMapping("viewMe")
	public String viewMe(Model model){
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);// 将机构部3，管理员4，教师5放进集合中
		pidList.add(4);
		List<TbCategory> list = categoryService.selectByPid(pidList);// 得到所有身份记录
		model.addAttribute("categoryList", list);//将身份封装进model
		return "admin/user/viewme";
	}
	
	/**
	 * @return
	 *  修改个人资料
	 */
	@RequestMapping("toUpdateMe")
	public ModelAndView toUpdateMe(){
		return new ModelAndView("admin/user/updateme");
	}

	/**
	 * @param user 以对象得到修改的值
	 * @param picture 得到用户替换的图片
	 * @param model 
	 * @return	修改个人资料
	 * @throws Exception 
	 */
	@RequestMapping("updateMe")
	public ModelAndView updateMe(TbUser user,MultipartFile picture,Model model) throws Exception{
		TbUser me = (TbUser)session.getAttribute("admin");
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
				session.setAttribute("admin", user);
			} else {
				model.addAttribute("message", "修改失败");
			}
		}
		return new ModelAndView("admin/user/viewme");
	}
	
	/**
	 * @return
	 * 到达修改个人密码界面
	 */
	@RequestMapping("toUpdatePsw")
	public ModelAndView toUpdatePsw(){
		return new ModelAndView("admin/user/updatepsw");
	}
	
	/**
	 * @param oldPsw 旧密码
	 * @param password 新密码
	 * @param model
	 * @return 修改密码
	 */
	@RequestMapping("updatePsw")
	public ModelAndView updatePsw(String oldPsw,String password,Model model){
		TbUser me = (TbUser)session.getAttribute("admin");
		if(me.getUsPassword().equals(oldPsw)){
			me.setUsPassword(password);
			if(userService.updateByPrimaryKey(me)==1){
				model.addAttribute("message", "修改成功");
				session.setAttribute("admin", me);
			}
			return new ModelAndView("admin/user/viewme");
		}
		model.addAttribute("message", "旧密码错误");
		return new ModelAndView("admin/user/updatepsw");
	}
	
	@RequestMapping("toWelcome")
	public String toWelcome() {
		return "admin/animation";
	}
	
	// 用于富文本编辑器的图片上传
	@RequestMapping("uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response) throws Exception {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
		String fileName = userService.uploadPicture(file, path);
		// 返回图片的URL地址
		response.getWriter().write(session.getServletContext().getContextPath()+"/static/uploadimg/" + fileName);
	}

	/**
	 * ajax上传图片，以json响应
	 * 
	 * @throws @ResponseBody
	 *             返回json数据，将pojo数据转化为json
	 * @throws IOException
	 * 
	 * 			@RequestMapping("uploadPicture") public @ResponseBody
	 *             Map<String, Object> uploadPicture(MultipartFile picture)
	 *             throws IllegalStateException, IOException { String msg =
	 *             "上传成功"; if (picture == null) { msg = "上传失败，服务器繁忙"; } else {
	 *             String path =
	 *             session.getServletContext().getRealPath("/static/uploadimg");
	 *             String fileName = UUID.randomUUID() +
	 *             picture.getOriginalFilename();// uuid避免重名 File uploadFile =
	 *             new File(path, fileName); picture.transferTo(uploadFile); }
	 *             // 将map对象转换成json类型数据 Map<String, Object> map = new
	 *             HashMap<String, Object>(); map.put("msg", msg); return map; }
	 */

}
