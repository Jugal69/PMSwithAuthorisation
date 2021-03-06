
package org.crce.interns.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.crce.interns.beans.FacultyUserBean;
import org.crce.interns.beans.UserDetailsBean;
import org.crce.interns.model.UserDetails;
import org.crce.interns.service.AssignTPCService;
import org.crce.interns.validators.AddTPCTaskValidator;
import org.crce.interns.validators.AddTPCValidator;
import org.crce.interns.validators.RemoveTPCValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AssignTPCController {
	@Autowired
	private AssignTPCService userService;

	@Autowired
	AddTPCValidator validator;

	@Autowired
	RemoveTPCValidator rvmvalidator;

	@Autowired
	AddTPCTaskValidator addTPCTaskValidator;


	@RequestMapping(value = "/TPOHome", method = RequestMethod.GET)
	public ModelAndView goTPOHome(HttpServletRequest request,@ModelAttribute("command") FacultyUserBean userBean, BindingResult result) {
		
		System.out.println("In TPO Home Page\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
			return new ModelAndView("TPO");
	}
	
	@RequestMapping(value="/ViewUsersT", method = RequestMethod.GET)
	public ModelAndView viewUsers(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
		{
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("users", userService.viewUsers());
		return new ModelAndView("viewUserT", modelMap);
		}
	}
	
	@RequestMapping(value="/ViewFacultyTasks", method = RequestMethod.GET)
	public ModelAndView viewFacultyTasks(HttpServletRequest request) {
		
		System.out.println("In View TPC Tasks\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
		{	
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("fusers", userService.viewFacultyTasks());
			return new ModelAndView("viewFacultyTasks", modelMap);
		}
	}
	
	
	@RequestMapping(value = "/InsertWork", method = RequestMethod.GET)
	public ModelAndView createUserWork(HttpServletRequest request,@ModelAttribute("command") FacultyUserBean userBean, BindingResult result) {
		System.out.println("In Assign TPC Work\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
			return new ModelAndView("insertWork");
	}
	
	@RequestMapping(value = "/AssignTPC", method = RequestMethod.GET)
	public ModelAndView assignTPC(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult result) {
		System.out.println("In Assign TPC\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")||role.equals("4")))
			return new ModelAndView("403");
		else
			return new ModelAndView("assignTPC");
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/RemoveTPC", method = RequestMethod.GET)
	public ModelAndView removeTPC(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult result) {
		System.out.println("In Remove TPC\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
			return new ModelAndView("removeTPC");
	}

	@RequestMapping(value = "/SubmitAssignTPC", method = RequestMethod.POST)
	public ModelAndView submitAssignTPC(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean,/*@ModelAttribute("fuserBean")FacultyUserBean fuserBean,*/ BindingResult bindingResult) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
		{	
			validator.validate(userBean, bindingResult);
			System.out.println("In Submit Assign TPC\n");
			if (bindingResult.hasErrors()) {
				System.out.println("Binding Errors are present...");
				return new ModelAndView("assignTPC");
			}
		//System.out.println("Task Assigned is "+fuserBean.getUserWork());
			int a;
			a=userService.assignTPC(userBean);
			FacultyUserBean fuserBean= new FacultyUserBean();
			
			return new ModelAndView("redirect:/ViewUsersT");
		}
		//return new ModelAndView("redirect:/TPOHome");
	}
	
		
	
	@RequestMapping(value = "/SubmitInsertWork", method = RequestMethod.POST)
	public ModelAndView createWork(HttpServletRequest request,@ModelAttribute("command") FacultyUserBean fuserBean, BindingResult bindingResult) {
		/*validator.validate(fuserBean, bindingResult);*/
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
		{
			addTPCTaskValidator.validate(fuserBean, bindingResult);
			System.out.println("In Submit TPC Work\n");
			if (bindingResult.hasErrors()) {
			System.out.println("Binding Errors are present...");
			return new ModelAndView("insertWork");
			}
			System.out.println("Username in Controller :"+fuserBean.getUserName());
			userService.insertWork(fuserBean);

			return new ModelAndView("redirect:/ViewFacultyTasks");
		}
	}
		
	
	@RequestMapping(value = "/SubmitRemoveTPC", method = RequestMethod.POST)
	public ModelAndView submitRemoveTPC(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult bindingResult) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")||role.equals("5")))
			return new ModelAndView("403");
		else
		{
			rvmvalidator.validate(userBean, bindingResult);
			System.out.println("In Submit Remove TPC\n");
			if (bindingResult.hasErrors()) {
			System.out.println("Binding Errors are present...");
			return new ModelAndView("removeTPC");
			}
			userService.removeTPC(userBean);
			return new ModelAndView("redirect:/ViewUsersT");
		}
	//	return new ModelAndView("redirect:/TPOHome");
	}

}

