package pl.wroc.pwr.agile.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @ModelAttribute("user")
    public User construct() {
        return new User();
    }
    
    @RequestMapping(value="/createDeputy", method=RequestMethod.POST)
    public String submitCreateDeputy(@ModelAttribute("user") User user) {
        user.setPassword("bugi");
        userService.save(user);
        return "user-detail";
    }
    
    @RequestMapping(value="/updatePassword", method=RequestMethod.POST)
    public String submitChangePassword(@ModelAttribute("user") User userWithNewPassword, Principal principal) {
        userService.updatePassword(principal.getName(), userWithNewPassword.getPassword());
        return "user-detail";
    }
    
    @RequestMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }
    
    @RequestMapping("/users/{id}")
    public String detail(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.findOne(id));
        return "user-detail";
    }
    
    @RequestMapping("/register")
    public String showRegister() {
        return "user-register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String doRegister(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/register.html?success=true";
    }
    
    @RequestMapping("/account")
    public String account(Model model, Principal principal) {
        String name = principal.getName();
        model.addAttribute("user", userService.findOne(name));
        return "user-detail";
    }
    

}
