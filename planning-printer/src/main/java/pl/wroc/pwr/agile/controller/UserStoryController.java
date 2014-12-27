package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.TaskRepository;
import pl.wroc.pwr.agile.repository.UserStoryRepository;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.UserStoryService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class UserStoryController {

	@Autowired
	private UserStoryService userStoryService; 
	
	@Autowired
	private UserService userService;
	 
	@Autowired
	private WorkspaceService workspaceService;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@ModelAttribute("task")
	public Task construct() {
	    return new Task();
	}
	
	 @RequestMapping("/story")
	 @Transactional(readOnly=true)
	 public String showStory(Principal principal, Model model) {
		 Workspace workspace = userService.findOne(principal.getName()).getWorkspace();
		 List<UserStory> userStories = workspaceService.findAllUserStories(workspace.getId());
		 
		 System.out.println("wika" + principal.getName());
		 System.out.println("wika" + workspace.toString());
		 System.out.println("wika task size"+ userStories.get(0).getTasks().size());
		 
	List<Task> tasksByUserStory = userStoryService.getTasksByUserStoryId(userStories.get(0).getId());
	
	System.out.println(tasksByUserStory.size());
	
	for (UserStory story : userStories) {
		System.out.println(story.getSummary());
		
		List<Task> tasks = story.getTasks();
		
		for (Task task : tasks) {
			System.out.println(task.getSummary());
		}
		
		System.out.println();
		
	}
	
		 
	    if (!userStories.isEmpty()) {
	    	model.addAttribute("stories",userStories);
		}
	    return "story";
	 }
	 
	 @Autowired
	 private UserStoryRepository repo;
	 

	@RequestMapping(method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("task") Task task,
			BindingResult result, @RequestParam Integer storyId) {
		if (result.hasErrors()) {
			return "story";
		}

		UserStory userStoryById = userStoryService.getUserStoryById(storyId);
		System.out.println(userStoryById.getSummary());
		task.setUserStory(userStoryById);
		taskRepository.save(task);
		
		

		return "redirect:/story.html?success=true";
	}
	 
	 
}
