package pl.wroc.pwr.agile.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import pl.wroc.pwr.agile.service.TaskService;
import pl.wroc.pwr.agile.service.UserStoryService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @Autowired
    private UserStoryService userStoryService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String step3(Model model) {
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }
        return "step3";
    }
    
    @RequestMapping(value="/setTaskComplete", method=RequestMethod.POST)
    @ResponseBody
    public String setTaskComplete(@RequestParam String taskId) {
        try {
            Task task = taskService.getTaskById(Integer.parseInt(taskId));
            task.setComplete(true);
            taskService.saveTask(task);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }
    
    @RequestMapping(value="/setTaskIncomplete", method=RequestMethod.POST)
    @ResponseBody
    public String setTaskIncomplete(@RequestParam String taskId) {
        try {
            Task task = taskService.getTaskById(Integer.parseInt(taskId));
            task.setComplete(false);
            taskService.saveTask(task);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }
    
    @RequestMapping(value="/addTask", method=RequestMethod.POST, produces = "text/html")
    public String addTask(@RequestParam String summary, @RequestParam String estimation,
            @RequestParam String storyId, @RequestParam String taskType, @RequestParam String number, 
            Model model) {
        Task task = new Task();
        
        UserStory userStoryById = userStoryService.getUserStoryById(Integer.parseInt(storyId));
        task.setNumber(number);
        task.setUserStory(userStoryById);
        task.setEstimation(Double.parseDouble(estimation));
        task.setSummary(summary);
        if (taskType.equals("DEV")) {
            task.setType(TaskType.DEVELOPER_TASK);
        } else {
            task.setType(TaskType.TESTER_TASK);
        }
        taskService.saveTask(task);
        
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }

        return "step3";
    }
    
    @RequestMapping(value = "/removeTask", produces = "text/html")
    public String removeTask(@RequestParam Integer id, Model model) {
        taskService.deleteTaskById(id);
        
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }

        return "step3";
    }
    
}
