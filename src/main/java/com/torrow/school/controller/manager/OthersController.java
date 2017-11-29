
package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbResource;

@Controller
@RequestMapping("/others")
public class OthersController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param model
	 * @return	查看考试时间
	 */
	@RequestMapping("managerTestTime")
	public ModelAndView managerTestTime(Model model){
		TbResource midExam = resourceService.selectByReTitle("期中考试");
		TbResource finalExam = resourceService.selectByReTitle("期末考试");
		TbResource catchingExam = resourceService.selectByReTitle("中招考试");
		model.addAttribute("midExam",midExam);
		model.addAttribute("finalExam",finalExam);
		model.addAttribute("catchingExam",catchingExam);
		return new ModelAndView("admin/others/managertesttime");
	}
	
	/**
	 * @param reId 要修改的时间id
	 * @param model
	 * @return	到达修改考试时间界面
	 */
	@RequestMapping("toUpdateTestTime")
	public ModelAndView toUpdateTestTime(int reId,Model model){
		TbResource updateTime = resourceService.selectByPrimaryKey(reId);
		model.addAttribute("updateTime", updateTime);			
		return new ModelAndView("admin/others/updatetesttime");
	}
	
	/**
	 * @param reId 要修改的时间id
	 * @param reContent 修改后的时间
	 * @param model
	 * @return	到达修改考试时间界面
	 */
	@RequestMapping("updateTestTime")
	public ModelAndView updateTestTime(int reId,String reContent,Model model){
		TbResource updateTime = resourceService.selectByPrimaryKey(reId);
		String message = "该考试已被删除，修改失败";
		if(updateTime!=null){
			updateTime.setReContent(reContent);
			int boo = resourceService.updateByPrimaryKey(updateTime);
			if(boo == 1){
				message = "修改成功";
			} else{
				message = "修改失败";
			}
		}
		model.addAttribute("message", message);
		return this.managerTestTime(model);
	}
	
	/**
	 * @param model
	 * @return	查看联系我们中联系方式
	 */
	@RequestMapping("managerInformation")
	public ModelAndView managerInformation(Model model){
		this.allInformation(model);
		return new ModelAndView("admin/others/managerinformation"); 
	}
	
	/**
	 * @param model
	 * @return 修改联系方式
	 */
	@RequestMapping("toUpdateInformation")
	public ModelAndView toUpdateInformation(Model model){
		this.allInformation(model);
		return new ModelAndView("admin/others/updateinformation");
	}
	
	
	/**
	 * @param reId	要修改的联系方式id
	 * @param reContent 修改后的内容
	 * @param model 
	 * @return
	 */
	@RequestMapping("updateInformation")
	public ModelAndView updateInformation(int reId,String reContent,Model model){
		TbResource updateInformation = resourceService.selectByPrimaryKey(reId);
		String message = "该联系方式已被删除，修改失败";
		if(updateInformation!=null){
			updateInformation.setReContent(reContent);
			int boo = resourceService.updateByPrimaryKey(updateInformation);
			if(boo == 1){
				message = "修改成功";
			} else{
				message = "修改失败";
			}
		}
		model.addAttribute("message", message);
		this.allInformation(model);
		return new ModelAndView("admin/others/managerinformation");
	}
	
	//得到联系方式并封装进model
	public void allInformation(Model model){
		TbResource phone = resourceService.selectByReTitle("电话");
		TbResource email = resourceService.selectByReTitle("邮箱");
		TbResource address = resourceService.selectByReTitle("地址");
		model.addAttribute("phone",phone);
		model.addAttribute("email",email);
		model.addAttribute("address",address);
	}
}
