
package com.torrow.school.controller.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
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
	 * model 
	 * @return 游客获得所有公告
	 */
	@RequestMapping("viewNotices")
	public ModelAndView viewNotices(Model model) {
		
		return new ModelAndView("visitor/notices");
	}

}
