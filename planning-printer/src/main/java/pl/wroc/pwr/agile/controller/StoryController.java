package pl.wroc.pwr.agile.controller;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.UserStory;
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
    public String addStory(@RequestParam String number,
            @RequestParam String summary, @RequestParam String points) {

        UserStory story = new UserStory();
        story.setNumber(number);
        story.setSummary(summary);
        story.setPoints(points);
        story.setWorkspace(userService.getLoggedUser().getWorkspace());
        story.setTasks(new HashSet<Task>());

        userStoryService.save(story);

        return story.getId().toString();
    }
	
	@RequestMapping("/saveTask")
	@ResponseBody
	public String saveTask(@RequestParam Integer storyId, @RequestParam String summary, @RequestParam Double estimation, @RequestParam TaskType type){
		
		Task task = new Task();
		task.setSummary(summary);
		task.setEstimation(estimation);
		task.setType(type);
		//userStoryService.addTask(storyId, task);
		return "true";
	}
	
	@RequestMapping("/removeStory")
	@ResponseBody
	public String removeStory(@RequestParam Integer id){
		userStoryService.delete(id);
		return "true";
	}
	
}
