package com.tony.springmvc.crud.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sun.org.apache.regexp.internal.recompile;
import com.tony.springmvc.crud.dao.DepartmentDao;
import com.tony.springmvc.crud.dao.EmployeeDao;
import com.tony.springmvc.crud.entities.Employee;
import com.tony.springmvc.test.UserNameNotMatchPasswordException;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DepartmentDao departmentDao;

	@ModelAttribute
	public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {

		if (id != null) {
			map.put("employee", employeeDao.get(id));
		}

	}

	@RequestMapping(value = "/emp", method = RequestMethod.PUT)
	public String update(Employee employee) {

		employeeDao.save(employee);

		return "redirect:/emps";
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());

		return "input";
	}

	@RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id) {

		employeeDao.delete(id);

		return "redirect:/emps";
	}

	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public String save(@Valid Employee employee, BindingResult result, Map<String, Object> map) {

		System.out.println(employee);

		if (result.getErrorCount() > 0) {
			System.out.println("Error!");

			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + ":" + error.getDefaultMessage());
			}

			// If there is error, then forward to a specific page
			map.put("departments", departmentDao.getDepartments());
			return "input";
		}

		employeeDao.save(employee);

		return "redirect:/emps";

	}

	@RequestMapping(value = "/emp", method = RequestMethod.GET)
	public String input(Map<String, Object> map) {
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());

		return "input";
	}

	@RequestMapping("/emps")
	public String list(Map<String, Object> map) {

		map.put("employees", employeeDao.getAll());

		return "list";
	}

	@ResponseBody
	@RequestMapping("/testJson")
	public Collection<Employee> testJson() {

		return employeeDao.getAll();

	}

	
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException{
		
		byte[] body = null;
		ServletContext servletContext  = session.getServletContext();
		InputStream inputStream = servletContext.getResourceAsStream("/files/try.txt");
		body = new byte[inputStream.available()];
		
		inputStream.read(body);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=try.txt");
		
		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
		
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping("/testHttpMessageConverter")
	public String testHttpMessageConverter(@RequestBody String body) {
		System.out.println(body);
		return "helloworld!" + new Date();
	}

	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	/**
	 * 
	 * 1. @ExceptionHandler It can add some Exception type arguments in this method, the argument corresponds the exception
	 * 2. @ExceptionHandler method cannot be put in Map. If want to display those exception message on jsp page, need to use ModelAndView as return value
	 * 3. @ExceptionHandler There priority for exceptions
	 * 4. @ExceptionHandler If cannot find @ExceptionHandler in current Handler,
	 * 	  then it is going to find @ExceptionHandler in the class has @ControllerAdvice to deal with Exception
	 */
	@ExceptionHandler({ArithmeticException.class})
	public ModelAndView handleArithmeticException(Exception ex){
		
		System.out.println("There are some errors: " + ex);
		
//		map.put("exception", ex);
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		
		return mv;
	}
	
	
	@RequestMapping("/i18n")
	public String testI18n(Locale locale){
		
		String val = messageSource.getMessage("i18n.user", null, locale);
		System.out.println(val);
		
		return "i18n";
	}
	
	@RequestMapping("/testTestFileUpload")
	public String testFileUpload(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException{
		
		System.out.println("desc: " + desc);
		System.out.println("OriginalFilename: " + file.getOriginalFilename());
		System.out.println("InputStream: " + file.getInputStream());
		
		return "success";
	}
	
	
	@RequestMapping("/testExceptionHandlerExceptionResolver")
	public String testExceptionHandlerExceptionResolver(@RequestParam("i") int i){
		
		
		System.out.println("result: " + (10/i));
		
		return "success";
	}
	
	@ResponseStatus(reason="test", value=HttpStatus.NOT_FOUND)
	@RequestMapping("/testResponseStatusExceptionResolver")
	public String testResponseStatusExceptionResolver(@RequestParam("i") int i){
		
		if(i == 13){
			throw new UserNameNotMatchPasswordException();
			
			
		}
		System.out.println("@testExceptionHandlerExceptionResolver...");
		
		
		return "success";
	}
	
	@RequestMapping(value="/testDefaultHandlerExceptionResolver", method=RequestMethod.POST)
	public String testDefaultHandlerExceptionResolver(){
		
		System.out.println("testDefaultHandlerExceptionResolver...");
		
		return "success";
	}
	
	@RequestMapping("/testSimpleMappingExceptionResolver")
	public String testSimpleMappingExceptionResolver(@RequestParam("i") int i){
		String [] vals = new String[10];
		
		System.out.println(vals[i]);
		return "success";
	}
	
	
	// @InitBinder
	// public void initBinder(WebDataBinder binder){
	//
	// binder.setDisallowedFields("lastName");
	//
	// }
	//

}
