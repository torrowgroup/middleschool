
package com.torrow.school.controller.visitor;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.torrow.school.entity.TbMessage;
import com.torrow.school.entity.TbResource;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PageBean;
/**
 * @author 张金高
 * 2017年11月7日下午1:44:56
 * 公告和教育教研控制层,追加留言,用户简介，成果
 */
@Controller
@RequestMapping("/visitorNEL")
public class NoticeEducateLiteratureController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param currentPage当前页码
	 * @param model 
	 * @param nId 通知公告的pId，因为首页情况下前台不知道公告的id
	 * @return 游客获得所有公告
	 * @throws ParseException 
	 */
	@RequestMapping("viewNotices")
	public ModelAndView viewNotices(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer nId) throws ParseException {
		List<TbCategory> recordList = categoryService.queryByPid(nId);//根据pId得到单个类别类
		TbCategory record = null;
		PageBean<TbResource> resourceLists = new PageBean<TbResource>();
		if(recordList==null||recordList.size()==0){
			return new ModelAndView("visitor/notices");
		} else {
			record = new TbCategory();
			record.setCaId(recordList.get(0).getCaId());
			resourceLists = resourceService.findingByPaging(currentPage, record,10);
		}
		categoryService.getCategory(record.getCaId(),model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		model.addAttribute("noticesList", resourceLists);
		return new ModelAndView("visitor/notices");
	}
	
	
	/**
	 * @param rId 用户查看的公告
	 * @param nId 通知公告id
	 * @param model
	 * @return 用户查看某一条公告
	 * @throws ParseException 
	 */
	@RequestMapping("noticeDetails")
	public ModelAndView noticeDetails(int rId,int nId,Model model) throws ParseException{
		TbResource notice = resourceService.selectByPrimaryKey(rId);
		categoryService.getCategory(nId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		model.addAttribute("resource", notice);
		return new ModelAndView("visitor/noticedetails");
	}

	/**
	 * @param rId 类别类id
	 * @return
	 * 	得到教研组内容
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("viewEducation")
	public ModelAndView viewEducation(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer rId,String inquiry) throws ParseException, UnsupportedEncodingException{
		if(inquiry!=null){
			inquiry = new String (inquiry.getBytes("iso8859-1"),"utf-8");			
		}
		TbCategory category = new TbCategory();
		category.setCaId(rId);
		category.setCaName(inquiry);
		PageBean<TbResource> resourceList = resourceService.findingByPaging(currentPage, category, 10);
		categoryService.getCategory(rId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		model.addAttribute("resourceList", resourceList);
		model.addAttribute("inquiry", inquiry);
		return new ModelAndView("visitor/education");
	}
	
	/**
	 * @param currentPage
	 * @param caId 
	 * ajax 实时刷新查询教育教研信息
	 */
	@RequestMapping("ajaxEducation")
	public @ResponseBody Map<String,Object> ajaxEducation(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			Model model,Integer caId,String inquiry) {
		Map<String, Object> map = new HashMap<String, Object>();
		TbCategory category = new TbCategory();
		category.setCaId(caId);
		category.setCaName(inquiry);
		PageBean<TbResource> resourceList = resourceService.findingByPaging(currentPage, category, 10);
		log.info("resourceList:"+resourceList);
		map.put("inquiry", inquiry);
		map.put("resourceList", resourceList);
		return map;
	}
	
	/**
	 * @param rId 下载文件的id
	 * 	下载教育教研,校园文学的内容
	 */
	@RequestMapping("downEduLiter")
	public void downEduLiter(int rId,HttpServletResponse response) throws Exception{
		resourceService.down(request, response, rId);
	}
	
	/**
	 * @param cId 用户想要查看的校园文学类别
	 * @param model
	 * @return 查看校园文学
	 * @throws ParseException 
	 */
	@RequestMapping("viewLiterature")
	public ModelAndView viewLiterature(@RequestParam(value = "currentPage", defaultValue = "1")int currentPage,int cId,Model model) throws ParseException{
		TbCategory category = new TbCategory();
		category.setCaId(cId);
		PageBean<TbResource> resourceList = resourceService.findingByPaging(currentPage, category, 10);
		model.addAttribute("resourceList", resourceList);
		categoryService.getCategory(cId,model);//将概括，新闻等封装进model，供下拉菜单使用，以及用户选择的功能项
		resourceService.getTimeInfor(model);//得到考试和联系信息
		return new ModelAndView("visitor/literature");
	}
	
	/**
	 * @throws ParseException 
	 * @return查看教师介绍,教师成果
	 */
	@RequestMapping("viewTeacherIntroActieve")
	public ModelAndView viewTeacherIntroduction(@RequestParam(value = "currentPage", defaultValue = "1")int currentPage,String ask,Model model) throws ParseException{
		categoryService.getCategory(0, model);
		resourceService.getTimeInfor(model);//得到考试和联系信息
		List<Integer> pidList = new ArrayList<Integer>();
		pidList.add(3);//教育教研组
		pidList.add(4);//用户
		List<TbCategory> categoryList = categoryService.selectByPid(pidList);
		model.addAttribute("categoryList",categoryList);
		PageBean<TbUser> pageBean = userService.findPageSplit(categoryList,currentPage,null, 12);
		model.addAttribute("userList", pageBean);
		String view = "visitor/teacherintroduction";//返回界面，默认是教师介绍
		if(ask.equals("achieve")){
			view = "visitor/teacherachieve";//返回界面，默认是教师成果
		}
		return new ModelAndView(view);
	}
	
	/**
	 * @param usId 查看详情的教师id
	 * @param model
	 * @return 查看教师的详情
	 * @throws ParseException 
	 */
	@RequestMapping("teacherDetail")
	public ModelAndView teacherDetail(int usId,Model model) throws ParseException{
		TbUser teacher = userService.selectById(usId); 
		model.addAttribute("teacher", teacher);
		categoryService.getCategory(0, model);
		resourceService.getTimeInfor(model);//得到考试和联系信息
		return new ModelAndView("visitor/teacherdetail");
	}
	
	/**
	 * @return	到达留言界面
	 * @throws ParseException 
	 */
	@RequestMapping("toLeaveWord")
	public ModelAndView toLeaveWord(Model model) throws ParseException{
		categoryService.getCategory(0, model);
		resourceService.getTimeInfor(model);//得到考试和联系信息
		return new ModelAndView("visitor/leaveword");
	}
	
	/**
	 * @param model
	 * @return	留言
	 * @throws ParseException
	 * String meTitle, String meHide, String meIssuingdate, String meStatus,
			String meContent
	 *  
	 */
	@RequestMapping("leaveWord")
	public ModelAndView leaveWord(String meHide,String meTitle,String meContent,Model model) throws ParseException{
		if(meHide==null||meHide.equals("")){
			meHide="匿名";
		}
		if(meTitle==null||meTitle.equals("")){
			meTitle = "无标题";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(new Date());
		TbMessage message = new TbMessage(meTitle,meHide,dateString,"未回复",meContent);
		int boo = messageService.insert(message);
		if(boo==1){
			model.addAttribute("message", "留言成功");
		} else {
			model.addAttribute("message", "留言失败");
		}
		categoryService.getCategory(0, model);
		resourceService.getTimeInfor(model);//得到考试和联系信息
		return new ModelAndView("visitor/leaveword");
	}
	
	/**
	 * @param model
	 * @return	查看留言
	 * @throws ParseException 
	 */
	@RequestMapping("viewLeaveWords")
	public ModelAndView viewLeaveWords(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, String inquiry,Model model) throws UnsupportedEncodingException, ParseException {
		if(inquiry!=null){
			inquiry = new String(inquiry.getBytes("iso8859-1"),"utf-8");
		}
		categoryService.getCategory(0, model);
		resourceService.getTimeInfor(model);//得到考试和联系信息
		model.addAttribute("messagePage", messageService.findPage(currentPage,inquiry,10));// 回显分页数据
		model.addAttribute("inquiry", inquiry);
		return new ModelAndView("visitor/watchwordleave");
	}
	
	// 用于富文本编辑器的图片上传
	@RequestMapping("uploadImg")
	public void uploadImg(MultipartFile file, HttpServletResponse response) throws Exception {
		String path = session.getServletContext().getRealPath("/static/uploadimg");
			String fileName = messageService.uploadImg(file,path);
		// 返回图片的URL地址
		response.getWriter().write("/middleschool/static/uploadimg/"+ fileName);
	}
}
