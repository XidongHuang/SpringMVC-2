package com.tony.springmvc.crud.handlers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tony.springmvc.crud.dao.DepartmentDao;
import com.tony.springmvc.crud.dao.EmployeeDao;
import com.tony.springmvc.crud.entities.Employee;

@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@ModelAttribute
	public void getEmployee(@RequestParam(value="id",required=false) Integer id, 
			Map<String, Object> map){
		
		if(id != null){
			map.put("employee", employeeDao.get(id));
		}
		
		
	}
	
	
	@RequestMapping(value="/emp", method=RequestMethod.PUT)
	public String update(Employee employee){
		
		employeeDao.save(employee);
		
		return "redirect:/emps";
	}
	
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.GET)
	public String input(@PathVariable("id") Integer id, Map<String, Object> map){
		map.put("employee", employeeDao.get(id));
		map.put("departments", departmentDao.getDepartments());
		
		
		return "input";
	}
	
	
	@RequestMapping(value="/emp/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		
		employeeDao.delete(id);
		
		return "redirect:/emps";
	}
	
	
	@RequestMapping(value="/emp", method=RequestMethod.POST)
	public String save(Employee employee){
		
		employeeDao.save(employee);
		
		return "redirect:/emps";
		
	}
	
	
	@RequestMapping(value="/emp", method=RequestMethod.GET)
	public String input(Map<String, Object> map){
		map.put("departments", departmentDao.getDepartments());
		map.put("employee", new Employee());
		
		return "input";
	}
	
	
	@RequestMapping("/emps")
	public String list(Map<String, Object> map){
		
		map.put("employees", employeeDao.getAll());
		
		return "list";
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		
//		binder.setDisallowedFields("lastName");
//		
//	}
//	
	
}
