
package com.torrow.school.controller.visitor;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
import com.torrow.school.util.PageBean;
/**
 * @author 张金高
 * 2017年11月7日下午1:44:56
 * 公告和教育教研控制层
 */
@Controller
@RequestMapping("/visitorNE")
public class NoticeEducateController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param currentPage当前页码
	 * @param model 
	 * @param nId 通知公告的pId，因为首页情况下前台不知道公告的id
	 * @return 游客获得所有公告
	 */
	@RequestMapping("viewNotices")
	public ModelAndView viewNotices(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer nId) {
		List<TbCategory> recordList = categoryService.queryByPid(nId);//根据pId得到单个类别类
		TbCategory record = null;
		PageBean<TbResource> resourceLists = new PageBean<TbResource>();
		if(recordList==null||recordList.size()==0){
			model.addAttribute("message", "暂时没有内容");
			return new ModelAndView("visitor/notices");
		} else {
			record = recordList.get(0);
			resourceLists = resourceService.findingByPaging(currentPage, record,10);
		}
		categoryService.getCategory(record.getCaId(),model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		model.addAttribute("noticesList", resourceLists);
		return new ModelAndView("visitor/notices");
	}
	
	
	/**
	 * @param rId 用户查看的公告
	 * @param nId 通知公告id
	 * @param model
	 * @return 用户查看某一条公告
	 */
	@RequestMapping("noticeDetails")
	public ModelAndView noticeDetails(int rId,int nId,Model model){
		TbResource notice = resourceService.selectByPrimaryKey(rId);
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		model.addAttribute("resource", notice);
		return new ModelAndView("visitor/noticedetails");
	}

	/**
	 * @param rId 类别类id
	 * @return
	 * 	得到教研组内容
	 */
	@RequestMapping("viewEducation")
	public ModelAndView viewEducation(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer rId){
		TbCategory category = new TbCategory();
		category.setCaId(rId);
		PageBean<TbResource> resourceList = resourceService.findingByPaging(currentPage, category, 10);
		log.info("category:"+category+" resourceList "+resourceList);
		categoryService.getCategory(rId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		model.addAttribute("resourceList", resourceList);
		return new ModelAndView("visitor/education");
	}
	
	
	/**
	 * @param rId 下载文件的id
	 * 	下载教育教研
	 */
	@RequestMapping("downEducation")
	public void downEducation(int rId,HttpServletResponse response) throws Exception{
		resourceService.down(request, response, rId);
	}
}
