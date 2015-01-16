package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.UserType;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

public class WorkspaceServiceTest {
    
    private static final String SAMPLE_EMAIL = "test@test.pl";
    
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
        
        assertThat(userStories.size(), is(2));
    }
    
    private Set<UserStory> getDummyUserStories() {
        Set<UserStory> userStories = new HashSet<UserStory>();
        
        UserStory userStory1 = Mockito.mock(UserStory.class);
        userStories.add(userStory1);
        
        UserStory userStory2 = Mockito.mock(UserStory.class);
        userStories.add(userStory2);
        
        return userStories;
    }

}
