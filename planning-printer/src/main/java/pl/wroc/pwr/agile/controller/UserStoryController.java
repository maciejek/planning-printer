package pl.wroc.pwr.agile.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserStoryController {

	
	 @RequestMapping("/story")
	    public String showStory(Principal principal, Model model) {
	        return "story";
	    }
	 
	 
	 
}
