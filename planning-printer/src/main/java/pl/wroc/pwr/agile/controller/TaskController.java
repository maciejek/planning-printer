package pl.wroc.pwr.agile.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
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
    public String step3(Model model, @RequestParam String tasksJSONObject, @RequestParam Boolean planning) {
        JSONObject jsonObject = new JSONObject(tasksJSONObject);
        JSONArray incompleteTasks = jsonObject.getJSONArray("incomplete_tasks");
        for (int i = 0; i < incompleteTasks.length(); ++i) {
            int taskId = incompleteTasks.getJSONObject(i).getInt("id");
            Task task = taskService.getTaskById(taskId);
            task.setComplete(false);
            taskService.saveTask(task);
        }
        JSONArray incompleteStories = jsonObject.getJSONArray("incomplete_stories");
        for (int i = 0; i < incompleteStories.length(); ++i) {
            int storyId = incompleteStories.getJSONObject(i).getInt("id");
            UserStory story = userStoryService.getUserStoryById(storyId);
            story.setComplete(false);
            userStoryService.save(story);
        }
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }
        model.addAttribute("planning", planning);
        return "step3";
    }
    
    @RequestMapping(value="/addTask", method=RequestMethod.POST, produces = "text/html")
    public String addTask(@RequestParam String summary, @RequestParam String estimation,
            @RequestParam String storyId, @RequestParam String taskType, @RequestParam String number, 
            Model model, @RequestParam Boolean planning) {
        Task task = new Task();
        
        UserStory userStoryById = userStoryService.getUserStoryById(Integer.parseInt(storyId));
        task.setNumber(number);
        task.setUserStory(userStoryById);
        task.setEstimation(Double.parseDouble(estimation));
        task.setSummary(summary);
        task.setComplete(false);
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

        model.addAttribute("planning", planning);
        return "step3";
    }
    
    @RequestMapping(value="/editTask", method=RequestMethod.POST, produces = "text/html")
    public String editTask(@RequestParam String summary, @RequestParam String estimation,
            @RequestParam String taskId, @RequestParam String taskType, @RequestParam String number, 
            Model model, @RequestParam Boolean planning) {
        Task task = taskService.getTaskById(Integer.parseInt(taskId));
        
        task.setNumber(number);
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

        model.addAttribute("planning", planning);
        return "step3";
    }
    
    @RequestMapping(value = "/removeTask", produces = "text/html")
    public String removeTask(@RequestParam Integer id, Model model, @RequestParam Boolean planning) {
        taskService.deleteTaskById(id);
        
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("stories", userStories);
        }

        model.addAttribute("planning", planning);
        return "step3";
    }
    
}
