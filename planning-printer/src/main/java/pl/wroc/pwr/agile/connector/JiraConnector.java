package pl.wroc.pwr.agile.connector;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Component
public class JiraConnector {


    public Set<String> getProjects(String jiraUrl, String jiraLogin, String jiraPassword) {
//        Set<String> result = new HashSet<String>();
//        try {
//            JiraRetreiver retreiver = new JiraRetreiver();
//            retreiver.connect(jiraUrl, jiraLogin, jiraPassword);
//            return retreiver.getProjects().keySet();
//        }
//        catch(Exception e) {
//            return result;
//        }
//    }
        return Sets.newHashSet("Planning Printer");
    }
    
    public Map<String, List<String>> getUserStoriesWithTasks(String jiraUrl, String jiraLogin, String jiraPassword, String projectId) {
        Map<String, List<String>> result = Maps.newLinkedHashMap();
        result.put("USER STORY 1", Lists.newArrayList("TASK 1"));
        return result;
    }
}
