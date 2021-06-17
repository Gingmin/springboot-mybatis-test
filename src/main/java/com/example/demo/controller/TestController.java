package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.service.SelectService;

@Controller
@RequestMapping("/*")
public class TestController {
	
	private SelectService selectService; 
	
	@Autowired
	public TestController(SelectService selectService) {
		this.selectService = selectService;
	}

	@GetMapping("/test2")
	public String defaultLocation(Model model) {
		
		return "home";
	}
	
	@GetMapping("/select")
	public String selectMethod(Model model) {
		
		int result = selectService.selectMethod();
		model.addAttribute("result", result);
		
		return "home";
	}
	
}
