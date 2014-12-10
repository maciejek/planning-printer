package pl.wroc.pwr.agile.controller;

import java.security.Principal;
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

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserType;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.InitDbService;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @RequestMapping(value="/createDeputy", method=RequestMethod.POST)
    public String submitCreateDeputy(Model model, Principal principal, @ModelAttribute("user") User deputyUser) {
        User currentUser = userService.findOne(principal.getName());
        deputyUser.setPassword(userService.encyptPassword("bugi"));
        Workspace currentWorkspace = currentUser.getWorkspace();
        deputyUser.setType(UserType.DEPUTY);
        deputyUser.setWorkspace(currentWorkspace);
        currentWorkspace.setDeputy(deputyUser);
        workspaceService.save(currentWorkspace);
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
    public String account(Model model, Principal principal) {
        String name = principal.getName();
        User currentUser = userService.findOne(name);
        model.addAttribute("user", currentUser);
        Logger logger = LoggerFactory.getLogger(InitDbService.class);
        logger.info(currentUser.toString());
        if (currentUser.getWorkspace().getDeputy() != null && currentUser.getWorkspace() != null) {
            model.addAttribute("deputy", currentUser.getWorkspace().getDeputy());
        }
        return "user-account";
    }
    

}
