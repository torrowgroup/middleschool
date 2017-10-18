package com.torrow.school.controller.manager;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbResource;

@Controller
@RequestMapping("/link")
public class BottomLinkController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return 添加底部链接的跳转
	 */
	@RequestMapping("linkJumping")
	public String linkJumping() {
		return "admin/addlink";
	}

	/**
	 * @param reTitle
	 * @param reContent
	 * @return 底部链接的添加
	 */
	@RequestMapping("addLink")
	public String addLink(String reTitle, String reContent, Model model) {
		TbResource record = new TbResource("底部链接", reTitle, reContent);
		resourceService.insert(record);
		model.addAttribute("message", "添加成功");
		return "admin/addlink";
	}

	/**
	 * @param model
	 * @return 链接的显示
	 */
	@RequestMapping("manageLink")
	public String manageLink(Model model) {
		List<TbResource> tbResource = resourceService.selectAll();
		for (TbResource item : tbResource) {
			if (item.getCaName().equals("底部链接")) {
				model.addAttribute("itemList", item);
			}
		}
		return "admin/managelink";
	}

	/**
	 * @param model
	 * @param id
	 * @return 查找单个链接
	 */
	@RequestMapping("selectOneLink")
	public String selectOnelink(Model model, Integer id) {
		TbResource tb = resourceService.selectByPrimaryKey(id);
		model.addAttribute("link", tb);
		return "admin/updatelink";
	}

	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @return 修改链接
	 */
	@RequestMapping("updateLink")
	public String updatelink(Model model, Integer id, String reTitle, String reContent) {
		TbResource record = resourceService.selectByPrimaryKey(id);
		record.setReTitle(reTitle);
		record.setReContent(reContent);
		int i = resourceService.updateByPrimaryKey(record);
		if (i != 0) {
			model.addAttribute("message", "修改成功");
		} else {
			model.addAttribute("message", "修改失败");
		}
		return this.manageLink(model);
	}

	/**
	 * @param model
	 * @param id
	 * @return 删除底部链接
	 */
	@RequestMapping("deleteLink")
	public String deleteLink(Model model, Integer id) {
		int i = resourceService.deleteByPrimaryKey(id);
		if (i != 0) {
			model.addAttribute("message", "删除成功");
		} else {
			model.addAttribute("message", "删除失败");
		}
		return this.manageLink(model);
	}

}
