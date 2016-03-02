package com.tony.springmvc.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SpringMVCTestExceptionHandler {
	
	@ExceptionHandler({ ArithmeticException.class })
	public ModelAndView handleArithmeticException(Exception ex) {

		System.out.println("There are some errors --->: " + ex);

		// map.put("exception", ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);

		return mv;
	}
	
}
