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

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
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
    public String removeFinishedTasks(Model model, @RequestParam String tasksJSONObject) {
        try {
            JSONObject jsonObject = new JSONObject(tasksJSONObject);
            JSONArray finishedTasks = jsonObject.getJSONArray("finished_tasks");
            for (int i = 0; i < finishedTasks.length(); ++i) {
                int taskId = finishedTasks.getJSONObject(i).getInt("id");
                int storyId = finishedTasks.getJSONObject(i).getInt("story_id");
                taskService.deleteTaskById(taskId);
                UserStory userStory = userStoryService.getUserStoryById(storyId);
                if (userStory.getTasks().isEmpty()) {
                    userStoryService.delete(userStory.getId());
                }
            }
            Workspace workspace = workspaceService.getCurrentWorkspace();
            List<UserStory> userStories = new ArrayList<UserStory>(workspace.getUserStories());
            if (!userStories.isEmpty()) {
                Collections.sort(userStories);
                model.addAttribute("stories", userStories);
            }
            return "step3";
        } catch(Exception e) {
            return "false";
        }
    }
    
    @RequestMapping(value="/addTask", method=RequestMethod.POST, produces = "text/html")
    public String addTask(@RequestParam String summary, @RequestParam String estimation,
            @RequestParam String storyId, @RequestParam String taskType, @RequestParam String number, 
            Model model) {
        try {
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
    
    @RequestMapping(value = "/removeTask", produces = "text/html")
    public String removeTask(@RequestParam Integer id, Model model){
        try {
            taskService.deleteTaskById(id);
            
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
    
    @RequestMapping(value="/editTask", method=RequestMethod.POST, produces = "text/html")
    public String editTask(@RequestParam String summary, @RequestParam String estimation,
            @RequestParam String taskId, @RequestParam String taskType, @RequestParam String number, 
            Model model) {
    	
    	  try {
              
    		  Task taskToEdit = taskService.getTaskById(Integer.parseInt(taskId));
    		  taskToEdit.setNumber(number);
    		  taskToEdit.setEstimation(Double.parseDouble(estimation));
    		  taskToEdit.setSummary(summary);
              if (taskType.equals("DEV")) {
            	  taskToEdit.setType(TaskType.DEVELOPER_TASK);
              } else {
            	  taskToEdit.setType(TaskType.TESTER_TASK);
              }
              taskService.saveTask(taskToEdit);
              
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
