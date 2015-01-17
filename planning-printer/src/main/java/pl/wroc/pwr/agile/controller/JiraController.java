package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.connector.JiraConnector;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.service.JiraService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class JiraController {
    
    @Autowired
    private JiraService jiraService;
    
    @Autowired
    private JiraConnector jiraConnector;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @ModelAttribute("deputy")
    public User construct() {
        return new User();
    }
    
    @RequestMapping(value="/setupJira", method=RequestMethod.POST)
    public String setupJira(Model model, 
            @RequestParam(value = "jiraUrl", required = false) String jiraUrl,
            @RequestParam(value = "jiraLogin", required = false) String jiraLogin,
            @RequestParam(value = "jiraPassword", required = false) String jiraPassword,
            Principal principal) {
        Set<String> projects = jiraConnector.getProjects(jiraUrl, jiraLogin, jiraPassword);
        if(!projects.isEmpty()) {
            User result = jiraService.saveJiraCredentials(jiraUrl, jiraLogin, jiraPassword);
            model.addAttribute("user", result);
            model.addAttribute("projects", projects);
            model.addAttribute("connectionSuccessful", true);
        }
        else {
            model.addAttribute("connectionFailed", true);
        }
        return "user-account";
    }
    
    @RequestMapping(value="/importFromJira", method=RequestMethod.POST, produces = "text/html" )
    public String importFromJira(Model model) {
        if(jiraService.importDataFromJira()) {
            model.addAttribute("importSucceed", true);
        }
        else {
            model.addAttribute("importFailure", true);
        }
        model.addAttribute("alreadyImported", true);
        List<UserStory> userStories = new ArrayList<UserStory>(workspaceService.findUserStoriesInWorkspace());
        if (!userStories.isEmpty()) {
            Collections.sort(userStories);
            model.addAttribute("userStories", userStories);
        }
        return "step2";
    }
    
    @RequestMapping(value="/projectSelected", method=RequestMethod.POST)
    @ResponseBody
    public String setTaskIncomplete(@RequestParam String projectNameParam) {
        try {
            jiraService.saveJiraProject(projectNameParam);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }
}
