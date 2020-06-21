package com.avinash.InstagramChatReader.Controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.avinash.InstagramChatReader.InstagramChatApplication;
import com.avinash.InstagramChatReader.Model.YAMLConfig;

@Controller
public class UserInterfaceController {
	@Autowired
	public YAMLConfig customConfig;
	
	public static String msgFile;
	public static String participant;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String loadIndexPage1() {
		msgFile = customConfig.getMessageFile();
		if(InstagramChatApplication.participant_argument != null) {
			participant = InstagramChatApplication.participant_argument;
		}else {
			participant = customConfig.getParticipant();
		}
		return "index";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String loadIndexPage2() {
		msgFile = customConfig.getMessageFile();
		participant = customConfig.getParticipant();
		return "index";
	}
	
	
}
