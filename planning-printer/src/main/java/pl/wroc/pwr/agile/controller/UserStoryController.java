package pl.wroc.pwr.agile.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
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
    
    @RequestMapping(value="/loadStep2", method = RequestMethod.POST, produces = "text/html")
    public String loadStep2(Model model) {
        try {
            Workspace workspace = workspaceService.getCurrentWorkspace();
            List<UserStory> userStories = new ArrayList<UserStory>(workspace.getUserStories());
            if (!userStories.isEmpty()) {
                Collections.sort(userStories);
                model.addAttribute("userStories", userStories);
            }
            return "step2";
        } catch(Exception e) {
            return "false";
        }
    }
    
    @RequestMapping(value = "/addStory", produces = "text/html")
    public String addStory(@RequestParam String number,
            @RequestParam String summary, @RequestParam String points, Model model) {
        
        try {
            UserStory story = new UserStory();
            story.setNumber(number);
            story.setSummary(summary);
            story.setPoints(points);
            story.setWorkspace(userService.getLoggedUser().getWorkspace());
            story.setTasks(new HashSet<Task>());
    
            userStoryService.save(story);
            
            Workspace workspace = workspaceService.getCurrentWorkspace();
            List<UserStory> userStories = new ArrayList<UserStory>(workspace.getUserStories());
            if (!userStories.isEmpty()) {
                Collections.sort(userStories);
                model.addAttribute("stories", userStories);
            }
    
            return "step3";
        } catch(Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
    
    @RequestMapping(value = "/removeStory", produces = "text/html")
    public String removeStory(@RequestParam Integer id, Model model) {
        try {
            userStoryService.delete(id);
            
            Workspace workspace = workspaceService.getCurrentWorkspace();
            List<UserStory> userStories = new ArrayList<UserStory>(workspace.getUserStories());
            if (!userStories.isEmpty()) {
                Collections.sort(userStories);
                model.addAttribute("stories", userStories);
            }
    
            return "step3";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

}
