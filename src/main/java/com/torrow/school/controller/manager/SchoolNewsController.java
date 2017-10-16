package com.torrow.school.controller.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbCategory;
import com.torrow.school.entity.TbResource;
@Controller
@RequestMapping("/news")
public class SchoolNewsController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping("newsJumping")
	public String newsJumping() {
		return "admin/addschoolnews";
	}
	
	
	
	
	/**
	 * @param model
	 * @param reTitle
	 * @param reContent
	 * @param caName
	 * @return 添加学校新闻
	 */
	@RequestMapping("addSchoolNews")
	public String addSchoolNews(Model model,String reTitle,String reContent,String caName){
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date(); 
		DateFormat d1 = DateFormat.getDateInstance(); //默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
		String str1 = d1.format(now);
		Date date = null;
		try {
			date = sdf.parse(str1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TbCategory> tbCategory=categoryService.selectAll();
		for(TbCategory item:tbCategory){
			if(item.getCaPid()==2){
				if(caName.equals(item.getCaName())){
					TbResource record=new TbResource(item.getCaId(),date,caName,reTitle,reContent);
					resourceService.insert(record);
					model.addAttribute("message","添加成功");
				}else{
					model.addAttribute("message","不存在该类别,添加失败");
				}
			}
		}
		return this.manageSchoolNews(1, model);
	}
	
	/**
	 * @param currentPage
	 * @param model
	 * @return 校园新闻类的管理
	 */
	@RequestMapping("manageSchoolNews")
	public String manageSchoolNews(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model){
		model.addAttribute("pagemsg",resourceService.findingByPaging(currentPage,2));//回显分页数据
		return "admin/manageschoolews";
	}
	
	/**
	 * @param id
	 * @param model
	 * @return 查询一条新闻
	 */
	@RequestMapping("selectOneNews")
	public String selectOneNews(int id,Model model) {
		TbResource tb=resourceService.selectByPrimaryKey(id);
		model.addAttribute("news",tb);
		return "admin/schoolnews/updateschoolnews";
	}
	
	/**
	 * @param model
	 * @param id
	 * @param reTitle
	 * @param reContent
	 * @return 修改学校新闻
	 */
	@RequestMapping("updateSchoolNews")
	public String updateSchoolNews(Model model,int id,String reTitle,String reContent){
		TbResource tb=resourceService.selectByPrimaryKey(id);
		TbResource record=new TbResource(tb.getReId(),tb.getCaId(),tb.getCaName(),reTitle,tb.getReIssuer(),tb.getReIssuingdate(),reContent);
		int i=resourceService.updateByPrimaryKey(record);
		if(i!=0) {
			model.addAttribute("message","修改成功");
		}else{
			model.addAttribute("message","修改失败");
		}
		return this.manageSchoolNews(1, model);
	}
	
	/**
	 * @param model
	 * @param id
	 * @return 删除学校新闻
	 */
	@RequestMapping("deleteNews")
	public String deleteNews(Model model,int id){
		int i=resourceService.deleteByPrimaryKey(id);
		if(i!=0) {
			model.addAttribute("message","删除成功");
		}else {
			model.addAttribute("message","删除失败");
		}
		return this.manageSchoolNews(1, model);
	}
}
