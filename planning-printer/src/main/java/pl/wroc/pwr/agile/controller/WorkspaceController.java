package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.service.UserService;
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
    public String doPlanning(Principal principal, Model model) {
        List<Employee> developers = workspaceService.findDevelopersInWorkspace();
        if (!developers.isEmpty()) {
            model.addAttribute("developers", developers);
        }
        List<Employee> testers = workspaceService.findTestersInWorkspace();
        if (!testers.isEmpty()) {
            model.addAttribute("testers", testers);
        }
        Collection<UserStory> userStories = workspaceService.findAllUserStories();
        logger.info("USER STORIES " + userStories.size());
        if (!userStories.isEmpty()) {
            model.addAttribute("userStories", userStories);
        }
        return "planning";
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
    
    @RequestMapping(value = "/planning/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadExcel() {
        // create some sample data
    	
    	System.out.println("222");
        List<Task> listTasks = new ArrayList<Task>();
        
        Task t = new Task();
        t.setSummary("qqqqqqqqqqqqqqq");
        listTasks.add(t);
        
        Task t2 = new Task();
        t2.setSummary("aaaaaaaaaaaaaaaaaaa");
        listTasks.add(t2);
        
        
        Task t3 = new Task();
        t3.setSummary("bbbbbbbbbbbbb");
        listTasks.add(t);
      
 
        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("pdfView", "listBooks", listTasks);
    }
    
}
