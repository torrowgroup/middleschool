package com.torrow.school.controller.manager;

import java.io.File;

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

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbUser;

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
	public String manageUser(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {
		model.addAttribute("pagemsg", userService.findPage(currentPage));// 回显分页数据
		return "admin/manageuser";
	}

	// 到达添加用户界面
	@RequestMapping("toAddUser")
	public String toAddUser(Model model) {
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);// 将机构部3，管理员4，教师5放进集合中
		pidList.add(4);
		pidList.add(5);
		List<TbCategory> list = categoryService.selectByPid(pidList);
		model.addAttribute("categoryList", list);
		return "admin/adduser";
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
		int boo = userService.addUser(tbUser);
		if (boo == 1) {
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "添加失败");
		}
		return this.toAddUser(model);
	}

	// 到达修改用户界面
	@RequestMapping("toUpdateUser")
	public String toUpdateUser(int id,int page, Model model) {
		TbUser tbUser = userService.selectById(id);
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);// 将机构部3，管理员4，教师5放进集合中
		pidList.add(4);
		pidList.add(5);
		List<TbCategory> list = categoryService.selectByPid(pidList);// 得到所有身份记录
		model.addAttribute("categoryList", list);
		model.addAttribute("user", tbUser);
		model.addAttribute("page", page);//得到当前页数并返回前台
		return "admin/updateuser";
	}

	//修改用户信息
	@RequestMapping("updateUser")
	public String updateUser(TbUser user,int page,MultipartFile picture,Model model) throws Exception{
		TbCategory category = categoryService.selectByPrimaryKey(user.getCaId());
		user.setCaName(category.getCaName());//得到用户身份名称
		if(!picture.getOriginalFilename().equals("")){//如果用户上传了图片，则换掉原来的图片 org.springframework.web.multipart.commons.CommonsMultipartFile@420199a9
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
			model.addAttribute("message", "修改完成");
		} else {
			model.addAttribute("message", "修改失败");
		}
		return this.manageUser(page, model);//返回管理用户当前页面
	}
	// 删除用户
	@RequestMapping("deleteUser")
	public String deleteUser(int id, Model model) {
		TbUser tbUser = userService.selectById(id);
		String path = session.getServletContext().getRealPath("static/uploadimg") + "/" + tbUser.getUsPicture();
		log.info("path  " + path);
		File files = new File(path);
		if (files.exists()) {
			files.delete();
		}
		String msg = "删除失败";
		if (userService.deleteById(id) == 1) {
			msg = "删除成功";
		}
		model.addAttribute("msg", msg);
		return this.manageUser(1, model);
	}
	
	@RequestMapping("viewMe")
	public String viewMe(Model model){
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);// 将机构部3，管理员4，教师5放进集合中
		pidList.add(4);
		pidList.add(5);
		List<TbCategory> list = categoryService.selectByPid(pidList);// 得到所有身份记录
		model.addAttribute("categoryList", list);//将身份封装进model
		return "admin/user/viewme";
	}
	
	/**
	 * @return
	 * 加载欢迎界面
	 */
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
		response.getWriter().write("/middleschool/static/uploadimg/" + fileName);
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
