package pl.wroc.pwr.agile.controller;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserType;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.InitDbService;
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
    public String submitCreateDeputy(Model model, Principal principal, @ModelAttribute("user") User deputyUser) {
        logger.info("===========================================userController");
        User currentUser = userService.findOne(principal.getName());
        deputyUser.setPassword(userService.encyptPassword("bugi"));
        Workspace currentWorkspace = currentUser.getWorkspace();
        currentWorkspace.setDeputy(deputyUser);
        workspaceService.save(currentWorkspace);
        //userService.save(deputyUser);
        model.addAttribute("deputyCreated", true);
        return "user-account";
    }
    
    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
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
        logger.info("account scrum master: " + currentUser.toString());
//        Map<UserType, User> deputyUser = currentUser.getWorkspace().getUsers();
//        if (deputyUser != null && currentUser.getWorkspace() != null) {
//            model.addAttribute("deputy", deputyUser);
//        }
        return "user-account";
    }
    

}
