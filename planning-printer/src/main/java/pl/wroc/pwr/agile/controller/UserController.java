package pl.wroc.pwr.agile.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @RequestMapping(value="/createDeputy", method=RequestMethod.POST)
    public String submitCreateDeputy(Model model, Principal principal, @ModelAttribute("user") User deputyUser) {
        User currentUser = userService.findOne(principal.getName());
        deputyUser.setPassword("bugi");
        Workspace currentWorkspace = currentUser.getWorkspace();
        //currentWorkspace.setDeputy(deputyUser);
        workspaceService.save(currentWorkspace);
        model.addAttribute("deputyCreated", true);
        return "user-account";
    }
    
    @RequestMapping(value="/updatePassword", method=RequestMethod.POST)
    public String submitChangePassword(Model model, 
    		@RequestParam(value = "oldPassword", required = false) String oldPassword,
    		@RequestParam(value = "newPassword", required = false) String newPassword,
    		@RequestParam(value = "passwordRepeated", required = false) String repeatedPassword,
    		Principal principal) {
    	if (newPassword.equals(repeatedPassword)) {
    		userService.updatePassword(principal.getName(), newPassword);
        	model.addAttribute("passwordChanged", true);
    	} else {
        	model.addAttribute("differentPasswords", true);
    	}
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
        //if (currentUser.getWorkspace().getDeputy() != null) {
        //    model.addAttribute("deputy", currentUser.getWorkspace().getDeputy());
        //}
        return "user-account";
    }
    

}
