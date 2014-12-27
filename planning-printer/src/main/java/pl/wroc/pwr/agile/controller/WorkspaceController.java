package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class WorkspaceController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
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
    
}
