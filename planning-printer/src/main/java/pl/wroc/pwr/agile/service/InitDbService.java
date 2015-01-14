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
        if (userRepository.findByEmail("bugi@pwr.edu.pl") == null) {
            User user1 = new User();
            user1.setEmail("bugi@pwr.edu.pl");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user1.setPassword(encoder.encode("bugi"));
            user1.setJiraLogin("tajemnica");
            user1.setJiraPassword("top secret");
            user1.setJiraUrl("http://176.119.37.132:9013");

            Workspace workspace = new Workspace();
            user1.setWorkspace(workspace);
            workspaceRepository.save(workspace);
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

            UserStory story = new UserStory();
            story.setNumber("US01");
            story.setSummary("Jako u¿ytkownik chcê ponownie wybraæ nieskoñczone taski.");
            story.setPoints("5");
            story.setWorkspace(workspace);

            userStoryRepository.save(story);

            Task t1 = new Task();
            t1.setNumber("T1");
            t1.setSummary("Widok 4 : widok wybierania nieskoñczonych tasków.");
            t1.setType(TaskType.DEVELOPER_TASK);
            t1.setEstimation(2.0D);
            t1.setUserStory(story);
            taskRepository.save(t1);

            Task t2 = new Task();
            t2.setNumber("T2");
            t2.setSummary("Logika dodawania tasków w kontrolerze widoku.");
            t2.setType(TaskType.DEVELOPER_TASK);
            t2.setEstimation(2.0D);
            t2.setUserStory(story);
            taskRepository.save(t2);


            UserStory story2 = new UserStory();
            story2.setNumber("US02");
            story2.setSummary("Jako u¿ytkownik chcê dodawaæ do user story taski wraz z estymacjami.");
            story2.setPoints("7");
            story2.setWorkspace(workspace);

            userStoryRepository.save(story2);

            Task t3 = new Task();
            t3.setNumber("T3");
            t3.setSummary("Rozszerzenie widoku dodawania user story o wigdety potrzebne do dodawania tasków w wybranym US.");
            t3.setType(TaskType.TESTER_TASK);
            t3.setEstimation(6.0D);
            t3.setUserStory(story2);
            taskRepository.save(t3);

        }

    }
}
