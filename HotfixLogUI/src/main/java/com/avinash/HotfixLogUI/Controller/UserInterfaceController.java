package com.avinash.HotfixLogUI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserInterfaceController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String loadIndexPage1() {
		return "index";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String loadIndexPage2() {
		return "index";
	}
	
	@RequestMapping(value="/HotfixLogUI", method=RequestMethod.GET)
	public String loadIndexPage() {
		return "index";
	}
	
	@RequestMapping(value="/HotfixLogUI", method=RequestMethod.POST)
	public String loadResultPage() {
		return "index";
	}
	
}
