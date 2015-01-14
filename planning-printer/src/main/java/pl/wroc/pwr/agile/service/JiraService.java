package pl.wroc.pwr.agile.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.connector.JiraConnector;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.UserRepository;

@Service
public class JiraService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserStoryService userStoryService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private JiraConnector jiraConnector;
    
    public User saveJiraCredentials(String jiraUrl, String jiraLogin, String jiraPassword) {
        User loggedUser = userService.getLoggedUser();
        loggedUser.setJiraLogin(jiraLogin);
        loggedUser.setJiraPassword(jiraPassword);
        loggedUser.setJiraUrl(jiraUrl);
        return userRepository.save(loggedUser);       
    }
    
    public User saveJiraProject(String projectName) {
        User loggedUser = userService.getLoggedUser();
        loggedUser.setJiraProject(projectName);
        return userRepository.save(loggedUser);
    }
    
    public boolean importDataFromJira() {
        try {
            User loggedUser = userService.getLoggedUser();
            transformJiraRawDataToUsAndTasks(jiraConnector.getUserStoriesWithTasks(loggedUser.getJiraUrl(), loggedUser.getJiraLogin(), loggedUser.getJiraPassword(), loggedUser.getJiraProject()));
            return true;
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
        
    }
    
    private void transformJiraRawDataToUsAndTasks(Map<String, Iterable<String>> rawJiraData) {
        for(String us : rawJiraData.keySet()) {
            int usId = userStoryService.save("", "?", us);
            for(String taskSummary : rawJiraData.get(us)) {
                Task task = new Task();
                task.setSummary(taskSummary);
                task.setUserStory(userStoryService.findById(usId));
                taskService.saveTask(task);
            }
        }
    }
}
