package com.torrow.school.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.torrow.school.base.BaseController;
import com.torrow.school.entity.TbUser;
import com.torrow.school.util.PagingVO;



/**
 * @author anlijie
 *	
 *2017年10月5日上午10:46:56
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * 初始化 “我的产品”列表 JSP页面,具有分页功能 
	 *  
	 * @param request 
	 * @param model 
	 * @return 
	 */  
	@RequestMapping(value = "showAllUser", method = RequestMethod.GET)  
	public String showAllUser(HttpServletRequest request, Model model) {   
	    //此处的productService是注入的IProductService接口的对象  
		System.out.println("333");
	    this.userService.showUsersByPage(request, model);  
	    System.out.println("88");
	    return "showUser";  
	}  
	
	@RequestMapping("main")
    public String  main(@RequestParam(value="currentPage",defaultValue="1",required=false)int currentPage,Model model){
        System.out.println("44"+"fuck");
		model.addAttribute("pagemsg", userService.findByPage(currentPage));//回显分页数据
        return "main";
    }
    
	 /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<学生操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    //  学生信息显示
    @RequestMapping("/showStudent")
    public String showStudent(Model model, Integer page) throws Exception {

        List<TbUser> list = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(userService.selectCount());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = userService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = userService.findByPaging(page);
        }

        model.addAttribute("studentList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showStudent";

    }
}
