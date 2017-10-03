package com.torrow.school.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class SchoolListener
 *
 */
@WebListener
public class SchoolListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SchoolListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent contextEvent)  { 
         // TODO Auto-generated method stub
    	//将根目录变量放入全局
    	String rootPath = contextEvent.getServletContext().getContextPath()+"/";
    	contextEvent.getServletContext().setAttribute("rootPath",rootPath);
    }
	
}
