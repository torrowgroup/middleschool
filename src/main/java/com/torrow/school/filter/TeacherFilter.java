package com.torrow.school.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.torrow.school.entity.TbUser;

public class TeacherFilter implements Filter{

	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override  
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
			  // 获得在下面代码中要用的request,response,session对象
	        HttpServletRequest servletRequest = (HttpServletRequest) request;
	        HttpSession session = servletRequest.getSession();
			//判断用户是否登录
			TbUser teacher=(TbUser)session.getAttribute("teacher");
			if(teacher != null) {
	        	 chain.doFilter(request, response);
	        }else {
	        	// 当用户为登录或登录超时时提醒并跳转到登录界面
	        	String returnUrl = servletRequest.getContextPath() + "/login";
				response.getWriter().println("<script language=\"javascript\">" + "alert(\"登录失效！请重新登录\");"
						+ "if(window.opener==null){window.top.location.href=\"" + returnUrl
						+ "\";}else{window.opener.top.location.href=\"" + returnUrl + "\";window.close();}</script>");
				return;
	        }
			
	}

	@Override
	public void destroy() {
		this.config = null;
	}

}
