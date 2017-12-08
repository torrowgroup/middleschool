package com.torrow.school.filter;
import java.io.IOException;  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletResponse;  
public class AllFilter implements Filter{  
    @Override  
    public void destroy() {  
          
    }  
  
    @Override  
    public void doFilter(ServletRequest req, ServletResponse res,  
            FilterChain chain) throws IOException, ServletException {  
    	//这么做是为了防止浏览器使用本地的缓存，在注销登录的时候通过回退按钮重新进入管理页面，	  
        HttpServletResponse hsr = (HttpServletResponse) res;  
        hsr.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.  
        hsr.setHeader("Pragma", "no-cache"); // HTTP 1.0.  
        hsr.setDateHeader("Expires", 0); // Proxies.  
        chain.doFilter(req, res);  
        //这么做是为了设置字符编码只限于post的
        req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf8");
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
          
    }  
}  
