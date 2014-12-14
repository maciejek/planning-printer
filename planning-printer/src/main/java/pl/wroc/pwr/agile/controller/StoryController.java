package pl.wroc.pwr.agile.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.UserStoryService;

@Controller
@RequestMapping("/story")
public class StoryController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserStoryService userStoryService;
	
	@RequestMapping("/addStory")
	@ResponseBody
	public String addStory(@RequestParam String number, @RequestParam String summary, @RequestParam String points){
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     Workspace workspace = userService.findOne(authentication.getName()).getWorkspace();
	            
	        UserStory story = new  UserStory();
	        story.setNumber(number);
	        story.setSummary(summary);
	        story.setPoints(points);
	        story.setWorkspace(workspace);
	        story.setTasks(new ArrayList<Task>());
	        
	        userStoryService.save(story);
	        
	        return story.getId().toString();
	}
	
	@RequestMapping("/removeStory")
	@ResponseBody
	public String removeStory(@RequestParam Integer id){
		userStoryService.delete(id);
		return "true";
	}
	
	
	
}
