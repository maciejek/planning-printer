package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.List;

import javax.resource.spi.work.Work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class WorkspaceController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @RequestMapping("/planning")
    public String showWorkspace(Principal principal, Model model) {
        Workspace workspace = userService.findOne(principal.getName()).getWorkspace();
        List<Employee> developers = workspaceService.findDevelopersInWorkspace(workspace.getId());
        if (!developers.isEmpty()) {
            model.addAttribute("developers", developers);
        }
        List<Employee> testers = workspaceService.findTestersInWorkspace(workspace.getId());
        if (!testers.isEmpty()) {
            model.addAttribute("testers", testers);
        }
        return "planning";
    }
    
    @RequestMapping("/createTeam")
    public String createTeam(Principal principal, Model model) {
        Workspace workspace = userService.findOne(principal.getName()).getWorkspace();
        List<Employee> developers = workspaceService.findDevelopersInWorkspace(workspace.getId());
        if (!developers.isEmpty()) {
            model.addAttribute("developers", developers);
        }
        List<Employee> testers = workspaceService.findTestersInWorkspace(workspace.getId());
        if (!testers.isEmpty()) {
            model.addAttribute("testers", testers);
        }
        return "create-team";
    }
    
}
