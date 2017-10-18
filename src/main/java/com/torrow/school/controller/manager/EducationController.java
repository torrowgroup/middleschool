package com.torrow.school.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.torrow.school.base.BaseController;

@Controller
@RequestMapping("/education")
public class EducationController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping("educationJumping")
	public String educationJumping() {
		return "admin/addEducation";
	}

}
