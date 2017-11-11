package com.torrow.school.controller.manager;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
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
	public String linkJumping(Model model) {
		int Pid = 8;
		List<TbCategory> tbCategory = categoryService.queryByPid(Pid);
		if (!tbCategory.isEmpty()) {
			for (TbCategory item : tbCategory) {
				model.addAttribute("link", item);
			}
		}
		return "admin/bottomlink/addlink";
	}

	/**
	 * @param reTitle
	 * @param reContent
	 * @return 底部链接的添加
	 */
	@RequestMapping("addLink")
	public String addLink(TbResource tbResource, Model model) {
		TbResource tbresource = resourceService.selectByCaId(tbResource.getCaId());
		if (null != tbresource) {
			model.addAttribute("message", "该类别已存在");
		} else {
			TbResource record = new TbResource(tbResource.getCaId(),tbResource.getCaName(), tbResource.getReTitle(),
					tbResource.getReContent());
			resourceService.insert(record);
			model.addAttribute("message", "添加成功");
		}
		return this.linkJumping(model);
	}

	/**
	 * @param model
	 * @return 链接的显示
	 */
	@RequestMapping("manageLink")
	public String manageLink(Model model) {
		int Pid = 8;
		List<TbCategory> tbCategory = categoryService.queryByPid(Pid);
		List<TbResource> tbResource = resourceService.selectAll();
		if (!tbCategory.isEmpty()) {
			for (TbResource item : tbResource) {
				for (TbCategory it : tbCategory) {
					if (item.getCaId().equals(it.getCaId())) {
						model.addAttribute("itemList", item);
					}
				}
			}
		}
		return "admin/bottomlink/managelink";
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
		return "admin/bottomlink/updatelink";
	}

	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @return 修改链接
	 */
	@RequestMapping("updateLink")
	public String updatelink(Model model, TbResource tbResource, Integer id) {
		TbResource record = resourceService.selectByPrimaryKey(id);
		record.setReTitle(tbResource.getReTitle());
		record.setReContent(tbResource.getReContent());
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
