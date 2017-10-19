
package com.torrow.school.base;
/**
 * @author 张金高
 */
import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import com.torrow.school.service.TbCategoryService;
import com.torrow.school.service.TbMessageService;
import com.torrow.school.service.TbResourceService;
import com.torrow.school.service.TbUserService;

public abstract class BaseController implements Serializable {

	/**
	 * 在父类中实现序列化，简化基本controller代码
	 */
	private static final long serialVersionUID = 1L;

	protected Logger log; // 日志

	public BaseController() {
		if (log == null) {
			log = Logger.getLogger(this.getClass());
		}
	}

	/*
	 * 服务接口
	 */
	@Resource
	protected TbCategoryService categoryService;
	@Resource
	protected TbMessageService messageService;
	@Resource
	protected TbResourceService resourceService;
	@Resource
	protected TbUserService userService;
	@Resource
	protected HttpServletRequest request;// 获得request
	// @Resource
	// protected HttpServletResponse response;//获得response
	@Resource
	protected HttpSession session; // 获得session

	/**
	 * 返回一个 ModelAndView 实例
	 * 
	 * @return
	 */
	protected ModelAndView getMV() {
		return new ModelAndView();
	}

	

}
