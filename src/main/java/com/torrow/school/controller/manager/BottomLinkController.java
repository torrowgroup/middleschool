package com.torrow.school.controller.manager;
import java.util.ArrayList;

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
	public String linkJumping(Model model) {
		
		return "admin/bottomlink/addlink";
	}

	/**
	 * @param reTitle
	 * @param reContent
	 * @return 底部链接的添加 控制次数4次 不需要做非空判断
	 */
	@RequestMapping("addLink")
	public String addLink(TbResource tbResource, Model model) {
		List<TbResource> list=new ArrayList<TbResource>();
		List<TbResource> t = resourceService.selectAll();
			for(TbResource en:t) {
				if("底部链接".equals(en.getCaName())) {
					list.add(en);
				}
			}
		//判断底部链接是否是4个
		if (list.size() > 3) {
			model.addAttribute("message", "底部链接最多有4个");
			return this.linkJumping(model);
		}
		TbResource record = new TbResource("底部链接",tbResource.getReTitle(),
				tbResource.getReContent());
		record.setCaId(0);
		int i = resourceService.insert(record);
		if (i != 0) {
			model.addAttribute("message", "添加成功");
		} else {
			model.addAttribute("message", "添加失败");
		}
		return this.linkJumping(model);
	}

	/**
	 * @param model
	 * @return 链接的显示
	 */
	@RequestMapping("manageLink")
	public String manageLink(Model model) {
		List<TbResource> list=new ArrayList<TbResource>();
		List<TbResource> t = resourceService.selectAll();
			for(TbResource en:t) {
				if("底部链接".equals(en.getCaName())) {
					list.add(en);
				}
			}
		model.addAttribute("list", list);
		if(list.isEmpty()) {
			model.addAttribute("sign",1);
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
		if(null!=tb) {
			model.addAttribute("link", tb);
		} else {
			model.addAttribute("message","没有该底部链接");
			return "admin/empty";
		}
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
