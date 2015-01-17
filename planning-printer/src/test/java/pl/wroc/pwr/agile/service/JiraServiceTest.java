package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.connector.JiraConnector;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.UserRepository;

import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;


public class JiraServiceTest {

    private static final String TASK2_SUMMARY = "t2";
    private static final String TASK1_SUMMARY = "t1";
    private static final String US_SUMMARY = "us1";
    private static final String JIRA_PROJECT = "jiraProject";
    private static final String JIRA_PASSWORD = "password";
    private static final String JIRA_LOGIN = "login";
    private static final String JIRA_URL = "url";

    @InjectMocks
    @Autowired
    private JiraService jiraService;
    
    @Mock
    private UserService userServiceMock;
    
    @Mock
    private UserRepository userRepositoryMock;
    
    @Mock
    private UserStoryService UserStoryServiceMock;
    
    @Mock
    private TaskService taskServiceMock;
    
    @Mock
    private JiraConnector JiraConnectorMock;
    
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldSaveJiraCredentialsToLoggedInUser() {
        ArgumentCaptor<User> userToBeUpdatedWithJiraInfo = ArgumentCaptor.forClass(User.class);
        User sampleUser = new User();
        when(userServiceMock.getLoggedUser()).thenReturn(sampleUser);
        jiraService.saveJiraCredentials(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD);
        
        verify(userRepositoryMock).save(userToBeUpdatedWithJiraInfo.capture());
        
        assertThat(userToBeUpdatedWithJiraInfo.getValue().getJiraLogin(), is(JIRA_LOGIN));
        assertThat(userToBeUpdatedWithJiraInfo.getValue().getJiraUrl(), is(JIRA_URL));
        assertThat(userToBeUpdatedWithJiraInfo.getValue().getJiraPassword(), is(JIRA_PASSWORD));
    }
    
    @Test
    public void shouldSaveJiraProjectToLoggedInUser() {
        ArgumentCaptor<User> userToBeUpdatedWithJiraInfo = ArgumentCaptor.forClass(User.class);
        User sampleUser = new User();
        when(userServiceMock.getLoggedUser()).thenReturn(sampleUser);
        jiraService.saveJiraProject(JIRA_PROJECT);
        
        verify(userRepositoryMock).save(userToBeUpdatedWithJiraInfo.capture());
        
        assertThat(userToBeUpdatedWithJiraInfo.getValue().getJiraProject(), is(JIRA_PROJECT));
    }
    
    @Test
    public void shouldImportDataCorrectlyTransportJiraDataAndSaveIt() {
        ArgumentCaptor<Task> tasksToBeSaved = ArgumentCaptor.forClass(Task.class);
        Map<String, Iterable<String>> jiraData = generateJiraDataWithOneUsAndTwoTasks();
        attachReturnsToMocks(jiraData);
        
        boolean success = jiraService.importDataFromJira();
        
        verify(UserStoryServiceMock).save("", "?", US_SUMMARY);
        verify(taskServiceMock, Mockito.times(2)).saveTask(tasksToBeSaved.capture());
        assertEquals(tasksToBeSaved.getAllValues().get(0).getSummary(), TASK1_SUMMARY);
        assertEquals(tasksToBeSaved.getAllValues().get(1).getSummary(), TASK2_SUMMARY);
        assertThat(success, is(true));
    }

    private Map<String, Iterable<String>> generateJiraDataWithOneUsAndTwoTasks() {
        Map<String, Iterable<String>> jiraData = Maps.newHashMap();
        List<String> tasks = Lists.newArrayList(TASK1_SUMMARY, TASK2_SUMMARY);
        jiraData.put(US_SUMMARY, tasks);
        return jiraData;
    }
    
    private void attachReturnsToMocks(Map<String, Iterable<String>> jiraData) {
        when(userServiceMock.getLoggedUser()).thenReturn(getLoggedUserWithAllDataIn());
        when(JiraConnectorMock.getUserStoriesWithTasks(JIRA_URL, JIRA_LOGIN, JIRA_PASSWORD, JIRA_PROJECT)).thenReturn(jiraData);
    }
    
    private User getLoggedUserWithAllDataIn() {
        User user = new User();
        user.setJiraLogin(JIRA_LOGIN);
        user.setJiraPassword(JIRA_PASSWORD);
        user.setJiraProject(JIRA_PROJECT);
        user.setJiraUrl(JIRA_URL);
        return user;
    }
    
    @Test
    public void shouldReturnFalseIfThereIsNoDataFromJira() {
        Map<String, Iterable<String>> jiraData = Maps.newHashMap();
        attachReturnsToMocks(jiraData);
        
        boolean success = jiraService.importDataFromJira();
        
        assertThat(success, is(false));
    }
}
