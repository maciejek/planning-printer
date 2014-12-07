package pl.wroc.pwr.agile.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.service.UserService;

@Controller
public class PasswordController {

    @Autowired
    private UserService userService;
    
    @RequestMapping("/passwordCorrect")
    @ResponseBody
    public String passwordCorrect(@RequestParam String oldPassword) {
        Boolean isPasswordCorrect = isLoggedUserPasswordCorrect(oldPassword);
        return isPasswordCorrect.toString();
    }
    
    @RequestMapping(value="/updatePassword", method=RequestMethod.POST)
    public String submitChangePassword(Model model, 
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            Principal principal) {
        if (isLoggedUserPasswordCorrect(oldPassword) && newPassword.equals(confirmPassword)) {
            userService.updatePassword(principal.getName(), newPassword);
            model.addAttribute("passwordChanged", true);
        } else {
            model.addAttribute("passwordNotChanged", true);
        }
        model.addAttribute("user", userService.findOne(principal.getName()));
        return "user-account";
    }
    
    private boolean isLoggedUserPasswordCorrect(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User loggedUser = userService.findOne(authentication.getName());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(password, loggedUser.getPassword());
        }
        return false;
    }
}
