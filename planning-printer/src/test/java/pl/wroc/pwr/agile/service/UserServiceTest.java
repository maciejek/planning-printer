package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.UserRepository;

public class UserServiceTest {

    private static final int SAMPLE_ID = 99;
    private static final String SAMPLE_PASSWORD = "passwords";
    private static final String SAMPLE_EMAIL = "email@mail.com";
    
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
    public void shouldFindOneByEmailInvokeFindByEmailOnRepository() {
        service.findOne(SAMPLE_EMAIL);
        
        Mockito.verify(userRepositoryMock).findByEmail(SAMPLE_EMAIL);
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
    
    @Test
    public void shouldChangePasswordInRepositoryWhenUpdatingPassword() {
        User userToHaveChangedPassword = new User();
        userToHaveChangedPassword.setPassword(SAMPLE_PASSWORD);
        when(userRepositoryMock.findByEmail(SAMPLE_EMAIL)).thenReturn(userToHaveChangedPassword);
        when(userRepositoryMock.save(any(User.class))).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (User) args[0];
            }
          });
        
        User withChangedPassword = service.updatePassword(SAMPLE_EMAIL, "newPassword");
        
        assertThat(service.getEncoder().matches(SAMPLE_PASSWORD, withChangedPassword.getPassword()), is(false));
    }
}
