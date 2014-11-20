package pl.wroc.pwr.agile.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.wroc.pwr.agile.service.UserService;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("users", userService.findAll());
        return "login";
    }
}
