package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.TaskService;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.UserStoryService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class WorkspaceController {

    private static Logger logger = LoggerFactory.getLogger(WorkspaceController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @ModelAttribute("task")
    public Task construct() {
        return new Task();
    }
    
    @RequestMapping("/planning")
    public String doPlanning(Model model) {
        List<Employee> developers = workspaceService.findDevelopersInWorkspace();
        if (!developers.isEmpty()) {
            model.addAttribute("developers", developers);
        }
        List<Employee> testers = workspaceService.findTestersInWorkspace();
        if (!testers.isEmpty()) {
            model.addAttribute("testers", testers);
        }
        return "planning";
    }
    
    @RequestMapping("/replanning")
    public String doReplanning() {
        return "replanning";
    }
    
    @RequestMapping("/createTeam")
    public String createTeam(Principal principal, Model model) {
        List<Employee> developers = workspaceService.findDevelopersInWorkspace();
        if (!developers.isEmpty()) {
            model.addAttribute("developers", developers);
        }
        List<Employee> testers = workspaceService.findTestersInWorkspace();
        if (!testers.isEmpty()) {
            model.addAttribute("testers", testers);
        }
        return "create-team";
    }
    
    @RequestMapping(value="planning/loadStep2", method = RequestMethod.POST, produces = "text/html")
    public String loadStep2Planning(Model model) {
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findCompleteUserStories());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("userStories", userStories);
        }
        return "step2";
    }
    
    @RequestMapping(value = "/planning/finish")
    @ResponseBody
    public String finishPlanning() {
       workspaceService.deleteAllCompletedUserStoriesAndTasks();
       workspaceService.setAllTasksAndUserStoriesCompleted();
       return "true";
    }
    
    @RequestMapping(value = "/planning/downloadPDF")
    public ModelAndView downloadExcelAfterPlanning() {
       Set<UserStory> userStories = workspaceService.getCurrentWorkspace().getUserStories();
       List<UserStory> listStories = new ArrayList<UserStory>(userStories);
       return new ModelAndView("pdfView", "listStories", listStories);
    }
    
    @RequestMapping(value = "/replanning/downloadPDF")
    public ModelAndView downloadExcelAfterReplanning() {
       List<UserStory> listStories = new ArrayList<UserStory>(workspaceService.findIncompleteUserStories());
       workspaceService.setAllTasksAndUserStoriesCompleted();
       return new ModelAndView("pdfView", "listStories", listStories);
    }
    
}
