package pl.wroc.pwr.agile.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.TaskService;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.UserStoryService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
@RequestMapping("/story")
public class UserStoryController {
    private static Logger logger = LoggerFactory.getLogger(UserStoryController.class);

    @Autowired
    private UserStoryService userStoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private TaskService taskService;
    
    @RequestMapping(value = "/addStory", produces = "text/html")
    public String addStory(@RequestParam String number,
            @RequestParam String summary, @RequestParam String points, Model model, 
            @RequestParam Boolean planning) {
        
        UserStory story = new UserStory();
        story.setNumber(number);
        story.setSummary(summary);
        story.setPoints(points);
        story.setComplete(false);
        story.setWorkspace(userService.getLoggedUser().getWorkspace());
        story.setTasks(new HashSet<Task>());

        userStoryService.save(story);
        
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }

        model.addAttribute("planning", planning);
        return "step3";
    }
    
    @RequestMapping(value = "/removeStory", produces = "text/html")
    public String removeStory(@RequestParam Integer id, Model model, @RequestParam Boolean planning) {
        userStoryService.delete(id);
        
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }

        model.addAttribute("planning", planning);
        return "step3";
    }
    
    @RequestMapping(value="/editStory", method=RequestMethod.POST, produces = "text/html")
    public String editStory(@RequestParam String storyId, @RequestParam String number, 
            @RequestParam String summary, @RequestParam String points, Model model, 
            @RequestParam Boolean planning){
		
        UserStory storyToEdit = userStoryService.getUserStoryById(Integer.valueOf(storyId));
		storyToEdit.setNumber(number);
		storyToEdit.setPoints(points);
		storyToEdit.setSummary(summary);
		userStoryService.save(storyToEdit);
		
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }
        
        model.addAttribute("planning", planning);
		return "step3";
    }

}
