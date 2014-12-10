package pl.wroc.pwr.agile.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.service.EmployeeService;
import pl.wroc.pwr.agile.service.UserService;

@Controller
@RequestMapping("/workspace")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/addDeveloper")
    @ResponseBody
    public String addDeveloper(@RequestParam String name, @RequestParam String surname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Workspace workspace = userService.findOne(authentication.getName()).getWorkspace();
            
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setType(EmployeeType.DEVELOPER);
        employee.setWorkspace(workspace);
        employeeService.save(employee);
        
        return employee.getId().toString();
    }
    
    @RequestMapping("/addTester")
    @ResponseBody
    public String addTester(@RequestParam String name, @RequestParam String surname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Workspace workspace = userService.findOne(authentication.getName()).getWorkspace();
            
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setType(EmployeeType.TESTER);
        employee.setWorkspace(workspace);
        employeeService.save(employee);
        
        return employee.getId().toString();
    }
    
    @RequestMapping("/removeEmployee")
    @ResponseBody
    public String removeEmployee(@RequestParam Integer id) {
        employeeService.delete(id);
        return "true";
    }
}
