package com.tony.springmvc.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class FirstInterceptor implements HandlerInterceptor{

	
	
	/**
	 * 
	 * After render view
	 * 
	 * 
	 * release resources
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("[FirstInterceptor] afterHandle");
		
		
	}

	/**
	 * After invoked method, before render view
	 * 
	 * Can be consider to modify request attribute or view
	 * 
	 */
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
		System.out.println("[FirstInterceptor] postHandle");
	}

	
	
	/**
	 * 
	 * This method is invoked before target method
	 * If return value is true, then it will go to next interceptor and method
	 * 
	 * Can be considered to make authority, log
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("[FirstInterceptor] preHandle");
		return true;
	}

}
