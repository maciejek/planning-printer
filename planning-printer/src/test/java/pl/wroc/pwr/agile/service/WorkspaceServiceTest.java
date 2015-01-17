package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.UserType;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.TaskRepository;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

public class WorkspaceServiceTest {
    
    private static final String SAMPLE_EMAIL = "test@test.pl";
    private static final Integer SAMPLE_ID_1 = 97;
    private static final Integer SAMPLE_ID_2 = 98;
    private static final Integer SAMPLE_ID_3 = 99;
    
    @InjectMocks
    @Autowired
    private WorkspaceService workspaceService;
    
    @Mock
    private WorkspaceRepository workspaceRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;
    
    @Mock
    private User userMock;
    
    @Mock
    private UserService userServiceMock;
    
    @Mock
    private TaskService taskServiceMock;
    
    @Mock
    private UserStoryService userStoryServiceMock;
    
    private Workspace workspace;
    
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        workspace = new Workspace();
        when(userServiceMock.getLoggedUser()).thenReturn(userMock);
        when(workspaceService.getCurrentWorkspace()).thenReturn(workspace);
    }
    
    @Test
    public void shouldFindAllInvokeFindAllOnRepository() {
        workspaceService.findAll();
        
        Mockito.verify(workspaceRepositoryMock).findAll();
    }
    
    @Test
    public void shouldAssignDeputyToCurrentWorkspace() {
        ArgumentCaptor<Workspace> workspaceCaptor = ArgumentCaptor.forClass(Workspace.class);
        
        User sampleDeputy = new User();
        sampleDeputy.setEmail(SAMPLE_EMAIL);
        
        workspaceService.assignDeputy(sampleDeputy);
        
        verify(workspaceRepositoryMock).save(workspaceCaptor.capture());
        
        assertThat(workspaceCaptor.getValue().getDeputy(), notNullValue());
        assertThat(workspaceCaptor.getValue().getDeputy().getEmail(), is(SAMPLE_EMAIL));
        assertThat(workspaceCaptor.getValue().getDeputy().getType(), is(UserType.DEPUTY));
    }
    
    @Test
    public void shouldRemoveDeputy() {
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        
        User sampleDeputy = new User();
        sampleDeputy.setEmail(SAMPLE_EMAIL);
        sampleDeputy.setType(UserType.DEPUTY);
        workspace.setDeputy(sampleDeputy);
        
        workspaceService.removeDeputy();
        
        verify(userRepositoryMock).delete(userCaptor.capture());
        
        assertThat(userCaptor.getValue(), notNullValue());
        assertThat(userCaptor.getValue().getEmail(), is(SAMPLE_EMAIL));
        assertThat(userCaptor.getValue().getType(), is(UserType.DEPUTY));
    }
    
    @Test
    public void shouldFindDevelopersAssignedToCurrentWorkspace() {
        workspace.setEmployees(getDummyEmployees());
        
        List<Employee> developers = workspaceService.findDevelopersInWorkspace();
        
        assertThat(developers.size(), is(2));
    }
    
    @Test
    public void shouldFindTestersAssignedToCurrentWorkspace() {
        workspace.setEmployees(getDummyEmployees());
        
        List<Employee> testers = workspaceService.findTestersInWorkspace();
        
        assertThat(testers.size(), is(1));
    }
    
    private List<Employee> getDummyEmployees() {
        List<Employee> employees = new ArrayList<Employee>();
        
        Employee employee1 = new Employee();
        employee1.setType(EmployeeType.DEVELOPER);
        employees.add(employee1);
        
        Employee employee2 = new Employee();
        employee2.setType(EmployeeType.DEVELOPER);
        employees.add(employee2);
        
        Employee employee3 = new Employee();
        employee3.setType(EmployeeType.TESTER);
        employees.add(employee3);
        
        return employees;
    }
    
    @Test
    public void shouldReturnEmptyListIfNoUserStoriesAssigned() {
        Collection<UserStory> userStories = workspaceService.findUserStoriesInWorkspace();
        
        assertThat(userStories.size(), is(0));
    }
    
    @Test
    public void shouldFindAllUserStoriesAssignedToCurrentWorkspace() {
        workspace.setUserStories(getDummyUserStories());
        
        Collection<UserStory> userStories = workspaceService.findUserStoriesInWorkspace();
        
        assertThat(userStories.size(), is(3));
    }
    
    @Test
    public void shouldfindIncompleteUserStories() {
        workspace.setUserStories(getDummyUserStories());
        
        Collection<UserStory> userStories = workspaceService.findIncompleteUserStories();
        
        assertThat(userStories.size(), is(2));
    }
    
    @Test
    public void shouldFindIncompleteTasksInUserStory() {
        Set<Task> incompleteTasks = workspaceService.findIncompleteTasksInUserStory(getDummyUserStoryWithTasks());
        
        assertThat(incompleteTasks.size(), is(1));
    }
    
    @Test
    public void shouldFindCompleteUserStories() {
        workspace.setUserStories(getDummyUserStories());
        
        Collection<UserStory> userStories = workspaceService.findCompleteUserStories();
        
        assertThat(userStories.size(), is(1));
    }
    
    @Test
    public void shouldFindCompleteTasksInUserStory() {
        Set<Task> completeTasks = workspaceService.findCompleteTasksInUserStory(getDummyUserStoryWithTasks());
        
        assertThat(completeTasks.size(), is(2));
    }
    
    @Test
    public void shouldDeleteAllCompletedUserStoriesAndTasks() {
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        workspace.setUserStories(getDummyUserStories());
        
        workspaceService.deleteAllCompletedUserStoriesAndTasks();
        
        verify(userStoryServiceMock, times(2)).delete(idCaptor.capture());
        assertThat(idCaptor.getAllValues().size(), is(2));
    }
    
    @Test
    public void shouldDeleteAllCompletedTasksInUserStory() {
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        
        workspaceService.deleteAllCompletedTasksInUserStory(getDummyUserStoryWithTasks());
        
        verify(taskServiceMock, times(2)).deleteTaskById(idCaptor.capture());
        assertThat(idCaptor.getAllValues().size(), is(2));
    }
    
    @Test
    public void shouldSetAllTasksAndUserStoriesCompleted() {
        ArgumentCaptor<ArrayList<Task>> tasksCaptor = ArgumentCaptor.forClass((Class) List.class);
        ArgumentCaptor<UserStory> userStoryCaptor = ArgumentCaptor.forClass(UserStory.class);
        
        workspace.setUserStories(getDummyUserStories());
        
        workspaceService.setAllTasksAndUserStoriesCompleted();
        
        verify(taskServiceMock, times(3)).saveTasks(tasksCaptor.capture());
        verify(userStoryServiceMock, times(3)).save(userStoryCaptor.capture());
    }
    
    private Set<UserStory> getDummyUserStories() {
        Set<UserStory> userStories = new HashSet<UserStory>();
        
        userStories.add(getDummyUserStoryWithTasks());
        
        UserStory userStory1 = new UserStory();
        userStory1.setId(SAMPLE_ID_2);
        userStory1.setComplete(false);
        userStory1.setTasks(new HashSet<Task>());
        userStories.add(userStory1);
        
        UserStory userStory2 = new UserStory();
        userStory2.setId(SAMPLE_ID_3);
        userStory2.setComplete(true);
        userStory2.setTasks(new HashSet<Task>());
        userStories.add(userStory2);
        
        return userStories;
    }
    
    private UserStory getDummyUserStoryWithTasks() {
        UserStory userStory = new UserStory();
        userStory.setId(SAMPLE_ID_1);
        Set<Task> tasks = new HashSet<Task>();
        
        Task task1 = new Task();
        task1.setId(SAMPLE_ID_1);
        task1.setComplete(false);
        tasks.add(task1);
        
        Task task2 = new Task();
        task1.setId(SAMPLE_ID_2);
        tasks.add(task2);
        
        Task task3 = new Task();
        task1.setId(SAMPLE_ID_3);
        tasks.add(task3);
        
        userStory.setTasks(tasks);
        return userStory;
    }

}
