package com.torrow.school.filter;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	@Override  
    public void doFilter(ServletRequest req, ServletResponse res,  
            FilterChain chain) throws IOException, ServletException {  
			System.out.println("doFilter");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("int");
	}

}
