package pl.wroc.pwr.agile.service;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.EmployeeRepository;
import pl.wroc.pwr.agile.repository.TaskRepository;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.UserStoryRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class InitDbService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserStoryRepository userStoryRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @PostConstruct
    public void init() {
        User user1 = new User();
        user1.setEmail("bugi@pwr.edu.pl");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user1.setPassword(encoder.encode("bugi"));

        Workspace workspace = new Workspace();
        user1.setWorkspace(workspace);
        workspaceRepository.save(workspace);
        userRepository.save(user1);
        
        Employee employee1 = new Employee();
        employee1.setName("Dariusz");
        employee1.setSurname("Pławecki");
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
        employee3.setSurname("Poślednicka");
        employee3.setType(EmployeeType.TESTER);
        employee3.setWorkspace(workspace);
        employeeRepository.save(employee3);
        
        UserStory story = new UserStory();
        story.setNumber("US01");
        story.setSummary("Testowy opis");
        story.setPoints("5");
        story.setWorkspace(workspace);
        
        userStoryRepository.save(story);
     
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task t = new Task();
        t.setSummary("opis taska");
        t.setType(TaskType.DEVELOPER_TASK);
        t.setEstimation(2.0D);
        t.setUserStory(story);
        tasks.add(t);
        taskRepository.save(tasks);
        
        UserStory story2 = new UserStory();
        story2.setNumber("US02");
        story2.setSummary("Jako Maciek chce..");
        story2.setPoints("7");
        story2.setWorkspace(workspace);
        
        userStoryRepository.save(story2);
     
        ArrayList<Task> tasks2 = new ArrayList<Task>();
        Task t2 = new Task();
        t2.setSummary("opis innego taska");
        t2.setType(TaskType.TESTER_TASK);
        t2.setEstimation(6.0D);
        t2.setUserStory(story2);
        tasks2.add(t2);
        taskRepository.save(tasks2);
        
        
        
    }
}
