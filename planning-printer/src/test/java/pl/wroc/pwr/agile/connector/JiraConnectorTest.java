package pl.wroc.pwr.agile.connector;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.jiraExt.JiraRetreiver;

import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;


public class JiraConnectorTest {
    
    private static final String JIRA_PROJECT_NAME = "Planning Printer";
    private static final String JIRA_PROJECT_KEY = "PP";
    private static final String JIRA_PASSWORD = "password";
    private static final String JIRA_LOGIN = "login";
    private static final String JIRA_URL = "url";
    private static final String TASK2_SUMMARY = "t2";
    private static final String TASK1_SUMMARY = "t1";
    private static final String US_SUMMARY = "us1";

    @InjectMocks
    @Autowired
    private JiraConnector jiraConnector;
    
    @Mock
    private JiraRetreiver jiraRetreiverMock;
    
    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(jiraRetreiverMock.getProjects()).thenReturn(getJiraProjectsMapping());
    }
    
    @Test
    public void shouldReturnEmptySetWhenExceptionDuringConnectionOccurs() throws Exception {
        throwExceptionWhenConnectingToJira();
        
        Set<String> result = jiraConnector.getProjects(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD);
        
        assertTrue(result.isEmpty());
    }

    private void throwExceptionWhenConnectingToJira() throws URISyntaxException {
        Mockito.doThrow(new URISyntaxException("", "")).when(jiraRetreiverMock).connect(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD);
    }
   
    @Test
    public void shouldReturnNamesOfProjectsFromJira() {
        Set<String> result = jiraConnector.getProjects(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD);
       
        assertTrue(result.size()==1);
        assertTrue(result.contains(JIRA_PROJECT_NAME));
    }
    
    @Test
    public void shouldReturnEmptyMapWhenExceptionDuringConnectionOccurs() throws Exception {
        throwExceptionWhenConnectingToJira();
       
        Map<String, Iterable<String>> result = jiraConnector.getUserStoriesWithTasks(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD, JIRA_PROJECT_NAME);

        assertTrue(result.isEmpty());
    }
    
    @Test
    public void shouldReturnJiraUsAndTasksorGivenProjectName() throws Exception {
        Mockito.when(jiraRetreiverMock.retreive(JIRA_PROJECT_KEY)).thenReturn(getJiraUserStoriesWithTasksMapping());
        
        Map<String, Iterable<String>> result = jiraConnector.getUserStoriesWithTasks(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD, JIRA_PROJECT_NAME);
        assertTrue(result.size()==1);
        assertContainsTasks(result);
    }

    private void assertContainsTasks(Map<String, Iterable<String>> result) {
        List<String> resultTasks = Lists.newArrayList(result.get(US_SUMMARY));
        assertTrue(resultTasks.contains(TASK1_SUMMARY));
        assertTrue(resultTasks.contains(TASK2_SUMMARY));
    }
    
    
    private Map<String, String> getJiraProjectsMapping() {
        Map<String, String> jiraProjects = Maps.newHashMap();
        jiraProjects.put(JIRA_PROJECT_NAME, JIRA_PROJECT_KEY);
        return jiraProjects;
    }
    
    private Map<String, Iterable<String>> getJiraUserStoriesWithTasksMapping() {
        Map<String, Iterable<String>> jiraData = Maps.newHashMap();
        List<String> tasks = Lists.newArrayList(TASK1_SUMMARY, TASK2_SUMMARY);
        jiraData.put(US_SUMMARY, tasks);
        return jiraData;
    }
}
