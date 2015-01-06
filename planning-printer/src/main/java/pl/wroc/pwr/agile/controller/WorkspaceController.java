package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.servlet.ModelAndView;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
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
    
    @RequestMapping(value = "/planning/downloadPDF")
    public ModelAndView downloadExcel() {
    	
        // create some sample data
    	
    	System.out.println("222");
        Set<Task> listTasks = new HashSet<Task>();
        List<UserStory> listStories = new ArrayList<UserStory>();
        
        UserStory story = new UserStory();
        story.setNumber("US01");
        story.setPoints("3");
        story.setSummary("Jako użytkownik chcę się zalogować za pomocą Jiry");
        story.setWorkspace(userService.getLoggedUser().getWorkspace());
        
        UserStory story2 = new UserStory();
        story2.setNumber("US02");
        story2.setPoints("6");
        story2.setSummary("Jako user chce moc zmienic haslo");
        story2.setWorkspace(userService.getLoggedUser().getWorkspace());
        
        Task t = new Task();
        t.setType(TaskType.DEVELOPER_TASK);
        t.setNumber("T1");
        t.setEstimation(2D);
        t.setSummary("Strona logowania");
        listTasks.add(t);
        
        Task t2 = new Task();
        t2.setType(TaskType.DEVELOPER_TASK);
        t2.setNumber("T2");
        t2.setEstimation(3D);
        t2.setSummary("Logika strony logowania");
        listTasks.add(t2);
        
        
        Task t3 = new Task();
        t3.setType(TaskType.TESTER_TASK);
        t3.setNumber("T3");
        t3.setEstimation(5D);
        t3.setSummary("Testy jednostkowe do logowania");
        listTasks.add(t);
        
        story.setTasks(listTasks);
        listStories.add(story);
        listStories.add(story2);
      

        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("pdfView", "listStories", listStories);
    }
    
}
