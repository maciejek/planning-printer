package pl.wroc.pwr.agile.connector;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import pl.wroc.pwr.agile.jiraExt.JiraRetreiver;

import com.google.api.client.util.Maps;

@Component
public class JiraConnector {

    JiraRetreiver retreiver;
    
    @PostConstruct
    private void initJiraRetreiver() {
        retreiver = new JiraRetreiver();
    }
    
    public Set<String> getProjects(String jiraUrl, String jiraLogin, String jiraPassword) {
        Set<String> result = new HashSet<String>();
        try {
            retreiver.connect(jiraUrl, jiraLogin, jiraPassword);
            return retreiver.getProjects().keySet();
        }
        catch(Exception e) {
            return result;
        }
    }
    
    public Map<String, Iterable<String>> getUserStoriesWithTasks(String jiraUrl, String jiraLogin, String jiraPassword, String projectName) {
        Map<String, Iterable<String>> result = Maps.newLinkedHashMap();
        try {
            retreiver.connect(jiraUrl, jiraLogin, jiraPassword);
            return retreiver.retreive(getProjectIdForProjectName(projectName));
        }
        catch(Exception e) {
            return result;
        }
    }
    
    private String getProjectIdForProjectName(String projectName) throws InterruptedException, ExecutionException {
         return retreiver.getProjects().get(projectName);
    }
}
