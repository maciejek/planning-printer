package pl.wroc.pwr.agile.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.UserType;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
@Transactional
public class WorkspaceService {
    
	private static Logger logger = LoggerFactory.getLogger(WorkspaceService.class);

	@Autowired
	private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
    @Autowired
    private WorkspaceRepository workspaceRepository;
    
    public void save(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }
    
    public Workspace getCurrentWorkspace() {
        return userService.getLoggedUser().getWorkspace();
    }
    
    public void assignDeputy(User deputyUser) {
        Workspace currentWorkspace = getCurrentWorkspace();
        deputyUser.setPassword(userService.encryptPassword("bugi"));
        deputyUser.setType(UserType.DEPUTY);
        deputyUser.setWorkspace(currentWorkspace);
        currentWorkspace.setDeputy(deputyUser);
        userRepository.save(deputyUser);
        save(currentWorkspace);
    }
    
    public void removeDeputy() {
        Workspace currentWorkspace = getCurrentWorkspace();
        User deputy = currentWorkspace.getDeputy();
        userRepository.delete(deputy);
    }
    
    public List<Employee> findDevelopersInWorkspace() {
        List<Employee> developers = new LinkedList<Employee>();
        List<Employee> employees = getCurrentWorkspace().getEmployees();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.DEVELOPER) {
                developers.add(employee);
            }
        }
        return developers;
    }
    
    public List<Employee> findTestersInWorkspace() {
        List<Employee> testers = new LinkedList<Employee>();
        List<Employee> employees = getCurrentWorkspace().getEmployees();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.TESTER) {
                testers.add(employee);
            }
        }
        return testers;
    }
    
    public Collection<UserStory> findUserStoriesInWorkspace() {
        Collection<UserStory> userStories = getCurrentWorkspace().getUserStories();
        if (userStories == null) {
            return new ArrayList<UserStory>();
        } else {
            return userStories;
        }
    }
    
    public Collection<UserStory> findIncompleteUserStories() {
        Collection<UserStory> userStories = new ArrayList<UserStory>();
        for (UserStory userStory : findUserStoriesInWorkspace()) {
            Set<Task> tasks = findIncompleteTasksInUserStory(userStory);
            userStory.setTasks(tasks);
            userStories.add(userStory);
        }
        return userStories;
    }
    
    public Set<Task> findIncompleteTasksInUserStory(UserStory userStory) {
        Set<Task> incompleteTasks = new HashSet<Task>();
        for (Task task : userStory.getTasks()) {
            if (!task.getComplete()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }
}
