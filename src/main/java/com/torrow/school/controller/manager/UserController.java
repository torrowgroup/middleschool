package com.torrow.school.controller.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbResource;
import com.torrow.school.entity.TbUser;

/**
 * @author 安李杰
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
		List<TbResource> resourceList = resourceSerice.selectAll();
		log.info(resourceList);

		model.addAttribute("resourceList", resourceList);
		log.info("toadduser");
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
	@ResponseBody
	public String addUser(TbUser tbUser) {

		return "admin/adduser";
	}

	// 添加用户界面
	@RequestMapping("toUpdateUser")
	public String toUpdateUser(int id,Model model) {
		TbUser tbUser = userService.selectById(id);
		model.addAttribute("user", tbUser);
		return "admin/updateuser";
	}

	// 删除用户
	@RequestMapping("deleteUser")
	public String deleteUser(int id, Model model) {
		TbUser tbUser = userService.selectById(id);
		String path = session.getServletContext().getRealPath("static/uploadimg/") + tbUser.getUsPicture();
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

	/**
	 * ajax上传图片，以json响应
	 * 
	 * @throws @ResponseBody
	 *             返回json数据，将pojo数据转化为json
	 * @throws IOException
	 */
	@RequestMapping("uploadPicture")
	public @ResponseBody Map<String, Object> uploadPicture(MultipartFile picture)
			throws IllegalStateException, IOException {
		String msg = "上传成功";
		if (picture == null) {
			msg = "上传失败，服务器繁忙";
		} else {
			String path = session.getServletContext().getRealPath("/static/uploadimg");
			String fileName = UUID.randomUUID() + picture.getOriginalFilename();// uuid避免重名
			File uploadFile = new File(path, fileName);
			picture.transferTo(uploadFile);
		}
		// 将map对象转换成json类型数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", msg);
		return map;
	}

}
