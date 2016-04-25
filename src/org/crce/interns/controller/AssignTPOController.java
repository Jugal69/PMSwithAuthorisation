
package org.crce.interns.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.crce.interns.beans.FacultyUserBean;
import org.crce.interns.beans.NotifyForm;
import org.crce.interns.beans.UserDetailsBean;
import org.crce.interns.service.AssignTPOService;
import org.crce.interns.validators.AddTPOValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AssignTPOController {
	@Autowired
	private AssignTPOService userService;

	@Autowired
	AddTPOValidator validator;

	@RequestMapping(value = "/AdminHome", method = RequestMethod.GET)
	public ModelAndView goAdminHome(HttpServletRequest request,@ModelAttribute("command") FacultyUserBean userBean, BindingResult result) {
		System.out.println("In Admin Home Page\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		return new ModelAndView("Admin");
	}
	
	@RequestMapping(value = "/FTPCHome", method = RequestMethod.GET)
	public ModelAndView goFTPCHome(HttpServletRequest request,@ModelAttribute("notify") FacultyUserBean userBean, BindingResult result) {
		System.out.println("In Faculty TPC Home Page\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		return new ModelAndView("FacultyTPC");
	}
	
	@RequestMapping(value="/ViewUsersA", method = RequestMethod.GET)
	public ModelAndView viewUsers(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		{
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("users", userService.viewUsers());
		return new ModelAndView("viewUserA", modelMap);
		}
	}
	
	@RequestMapping(value="/ViewUsersF", method = RequestMethod.GET)
	public ModelAndView viewUsersF(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		{
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("users", userService.viewUsers());
			return new ModelAndView("viewUserF", modelMap);
		}
	}


	@RequestMapping(value = "/AssignTPCF", method = RequestMethod.GET)
	public ModelAndView assignTPCF(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult result) {
		System.out.println("In Assign TPC\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
			return new ModelAndView("assignTPCF");
	}
	
	@RequestMapping(value = "/AssignTPO", method = RequestMethod.GET)
	public ModelAndView assignTPO(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult result) {
		System.out.println("In Assign TPO\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		return new ModelAndView("assignTPO");	}

	
	@RequestMapping(value = "/RemoveTPO", method = RequestMethod.GET)
	public ModelAndView removeTPO(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult result) {
		System.out.println("In Remove TP0\n");
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
			return new ModelAndView("removeTPO");
	}

	@RequestMapping(value = "/SubmitAssignTPO", method = RequestMethod.POST)
	public ModelAndView createUser(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult bindingResult) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		{
		validator.validate(userBean, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Binding Errors are present...");
			return new ModelAndView("assignTPO");
		}
		userService.assignTPO(userBean);
		
		return new ModelAndView("redirect:/ViewUsersA");
		}
		//return new ModelAndView("redirect:/AdminHome");
	}
		
	@RequestMapping(value = "/SubmitAssignTPCF", method = RequestMethod.POST)
	public ModelAndView createTPCF(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult bindingResult) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		{
			validator.validate(userBean, bindingResult);
			if (bindingResult.hasErrors()) {
				System.out.println("Binding Errors are present...");
				return new ModelAndView("assignTPO");
			}
			userService.assignTPCF(userBean);
		//return new ModelAndView("redirect:/FTPCHome");
			return new ModelAndView("redirect:/ViewUsersF");
		}
		//return new ModelAndView("redirect:/AdminHome");
	}
	
	@RequestMapping(value = "/SubmitRemoveTPO", method = RequestMethod.POST)
	public ModelAndView deleteUser(HttpServletRequest request,@ModelAttribute("command") UserDetailsBean userBean, BindingResult bindingResult) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		if(!(role.equals("6")))
			return new ModelAndView("403");
		else
		{
			validator.validate(userBean, bindingResult);
			if (bindingResult.hasErrors()) {
				System.out.println("Binding Errors are present...");
				return new ModelAndView("removeTPO");
			}
			userService.removeTPO(userBean);
			return new ModelAndView("redirect:/ViewUsersA");
		}
		//return new ModelAndView("redirect:/AdminHome");
	}
}

