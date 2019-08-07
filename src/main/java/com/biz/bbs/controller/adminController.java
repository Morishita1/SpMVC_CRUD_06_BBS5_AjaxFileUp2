package com.biz.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class adminController {

	@RequestMapping(value = "/system", method = RequestMethod.GET)
	public String system() {
		
		return "home";
	}
	
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	public String member() {
		
		return "home";
	}
	@RequestMapping(value = "/bbs", method = RequestMethod.GET)
	public String bbs() {
		
		return "home";
	}
}
