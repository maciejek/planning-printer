package pl.wroc.pwr.agile.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkspaceService workspaceService;
    
    @ModelAttribute("user")
    public User construct() {
        return new User();
    }
    
    @RequestMapping
    public String showRegister() {
        return "user-register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user-register";
        }
        Workspace workspace = new Workspace();
        user.setWorkspace(workspace);
        userService.save(user);
        return "redirect:/register.html?success=true";
    }
    
    @RequestMapping("/available")
    @ResponseBody
    public String available(@RequestParam String email) {
        Boolean isEmailAvailable = userService.findOne(email) == null;
        return isEmailAvailable.toString();
    }
}
