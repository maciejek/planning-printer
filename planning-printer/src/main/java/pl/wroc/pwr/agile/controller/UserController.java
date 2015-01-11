package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @RequestMapping(value="/createDeputy", method=RequestMethod.POST)
    public String submitCreateDeputy(Model model, @ModelAttribute("user") User deputyUser) {
        workspaceService.assignDeputy(deputyUser);
        model.addAttribute("deputyCreated", true);
        return "user-account";
    }
    
    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        List<Workspace> workspaces =  workspaceService.findAll();
        for(Workspace w : workspaces) {
            logger.info(w.getUsers().toString());
        }
        return "users";
    }
    
    @RequestMapping("/users/{id}")
    public String detail(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.findOne(id));
        return "user-account";
    }
    
    @RequestMapping("/account")
    public String account(Model model) {
        User currentUser = userService.getLoggedUser();
        model.addAttribute("user", currentUser);
        logger.info(currentUser.toString());
        if (currentUser.getWorkspace().getDeputy() != null && currentUser.getWorkspace() != null) {
            model.addAttribute("deputy", currentUser.getWorkspace().getDeputy());
        }
        return "user-account";
    }
    
    @RequestMapping(value="/setupJira", method=RequestMethod.POST)
    public String setupJira(Model model, 
            @RequestParam(value = "jiraUrl", required = false) String jiraUrl,
            @RequestParam(value = "jiraLogin", required = false) String jiraLogin,
            @RequestParam(value = "jiraPassword", required = false) String jiraPassword,
            Principal principal) {
        boolean connectionSuccessful = connect(jiraUrl, jiraLogin, jiraPassword);
        List<String> projects = getProjects();
        if(connectionSuccessful) {
            User result = userService.saveJiraCredentials(jiraUrl, jiraLogin, jiraPassword);
            model.addAttribute("user", result);
            model.addAttribute("projects", projects);
        }
        return "user-account";
    }

    private List<String> getProjects() {
        List<String> result = new ArrayList<String>();
        result.add("Planning printer");
        return result;
    }

    private boolean connect(String jiraUrl, String jiraLogin,
            String jiraPassword) {
        return true;
    }


}
