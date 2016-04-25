
package org.crce.interns.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.crce.interns.beans.Event_detailsBean;
import org.crce.interns.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventController {
	Integer months;
	
	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/InsertMonth", method = RequestMethod.GET)
	public ModelAndView insertMonth(HttpServletRequest request,@ModelAttribute("command") Event_detailsBean edBean,BindingResult result) {
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		/*if(!(role.equals("4")||role.equals("5")))
			return new ModelAndView("403");
		else*/
			return new ModelAndView("insertMonth");
	}

	@RequestMapping(value = "/SubmitMonth", method = RequestMethod.POST)
	public ModelAndView submitMonth(HttpServletRequest request,@RequestParam("month") Integer month) {
		System.out.println("Month sent from front end :" + month);
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		/*if(!(role.equals("4")||role.equals("5")))
			return new ModelAndView("403");
		else
		{*/
			months = month;
			return new ModelAndView("redirect:/ViewEvents");
		
	}

	@RequestMapping(value="/ViewEvents", method = RequestMethod.GET)
	public ModelAndView viewEvents(HttpServletRequest request) {
		
		System.out.println("In View Events: " + months);
		HttpSession session=request.getSession();
		String role =  (String)session.getAttribute("roleId");
		/*if(!(role.equals("4")||role.equals("5")))
			return new ModelAndView("403");
		else*/
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("events", eventService.viewEvents(months));
			if (modelMap.isEmpty()) {
				System.out.println("Error no Model map, Model map is null");
				return new ModelAndView("403");
			}
			if(modelMap.containsValue(null))
			{
				System.out.println("No events in selected month");
				return new ModelAndView("noEvents");
			}
			
			return new ModelAndView("viewEvents", modelMap);
		}


}