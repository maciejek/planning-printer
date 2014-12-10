package pl.wroc.pwr.agile.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.EmployeeRepository;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
public class InitDbService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setEmail("bugi@pwr.edu.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user1.setPassword(encoder.encode("bugi"));

        Workspace workspace = new Workspace();
        workspaceRepository.save(workspace);
        user1.setWorkspace(workspace);
        userRepository.save(user1);
        
        Employee employee1 = new Employee();
        employee1.setName("Dariusz");
        employee1.setSurname("P³awecki");
        employee1.setType(EmployeeType.DEVELOPER);
        employee1.setWorkspace(workspace);
        employeeRepository.save(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Maciej");
        employee2.setSurname("Radoszko");
        employee2.setType(EmployeeType.DEVELOPER);
        employee2.setWorkspace(workspace);
        employeeRepository.save(employee2);
        
        Employee employee3 = new Employee();
        employee3.setName("Wiktoria");
        employee3.setSurname("Poœlednicka");
        employee3.setType(EmployeeType.TESTER);
        employee3.setWorkspace(workspace);
        employeeRepository.save(employee3);
    }
}
