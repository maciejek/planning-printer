package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;

import pl.wroc.pwr.agile.connector.JiraConnector;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.jiraExt.JiraRetreiver;
import pl.wroc.pwr.agile.service.JiraService;
import pl.wroc.pwr.agile.service.UserService;

@Controller
public class JiraController {
    private static Logger logger = LoggerFactory.getLogger(JiraController.class);
    
    @Autowired
    private JiraService jiraService;
    
    @Autowired
    private JiraConnector jiraConnector;
    
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
        }
        model.addAttribute("connectionSuccessful", !projects.isEmpty());
        model.addAttribute("mydeputy", null);
        return "user-account";
    }
    
    @RequestMapping(value="/importFromJira", method=RequestMethod.POST)
    @ResponseBody
    public String importFromJira() {
        return String.valueOf(jiraService.importDataFromJira());
    }
}
