package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.UserRepository;

public class UserServiceTest {

    private static final int SAMPLE_ID = 99;
    private static final String SAMPLE_PASSWORD = "passwords";
    
    @InjectMocks
    @Autowired
    private UserService service;
    
    @Mock
    private UserRepository userRepositoryMock;
    
    @Mock
    private WorkspaceService workspaceServiceMock;
    
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldFindAllInvokeFindAllOnRepository() {
        service.findAll();
        
        Mockito.verify(userRepositoryMock).findAll();
    }
    
    @Test
    public void shouldFindOneWithIdInvokeFindOneOnRepository() {
        service.findOne(SAMPLE_ID);
        
        verify(userRepositoryMock).findOne(SAMPLE_ID);
    }
    
    @Test
    public void shouldEncryptPaswordBeforeSavingNewUser() {
        User userToBeCreated = new User();
        when(userRepositoryMock.save(userToBeCreated)).thenReturn(userToBeCreated);
        userToBeCreated.setPassword(SAMPLE_PASSWORD);
        
        User newlySavedDeputy = service.registerUser(userToBeCreated);
        
        assertThat(service.getEncoder().matches(SAMPLE_PASSWORD, newlySavedDeputy.getPassword()), is(true));
    }
    
    @Ignore
    @Test
    public void shouldNewlySavedDeputyHaveTheSamePasswordAsTheOneGiven() {
        User userMock = mock(User.class);
        when(userMock.getPassword()).thenReturn(SAMPLE_PASSWORD);
        
        User newlySavedDeputy = service.registerUser(userMock);
        
        assertThat(newlySavedDeputy.getPassword(), not(SAMPLE_PASSWORD));
        verify(userRepositoryMock).save(newlySavedDeputy);      
        
    }
}
