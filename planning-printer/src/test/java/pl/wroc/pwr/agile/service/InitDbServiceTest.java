package pl.wroc.pwr.agile.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.repository.EmployeeRepository;
import pl.wroc.pwr.agile.repository.TaskRepository;
import pl.wroc.pwr.agile.repository.UserRepository;
import pl.wroc.pwr.agile.repository.UserStoryRepository;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;


public class InitDbServiceTest {
    
    @Mock
    private UserRepository userRepositoryMock;
    
    @Mock
    private WorkspaceRepository workspaceRepositoryMock;
    
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    
    @Mock
    private UserStoryRepository userStoryRepositoryMock;
    
    @Mock
    private TaskRepository taskRepositoryMock;
    
    @InjectMocks
    @Autowired
    InitDbService service;
    
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void dummyInitTest() {
        service.init();
    }
}
