package pl.wroc.pwr.agile.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.repository.UserRepository;


public class UserServiceTest {

    private static final int SAMPLE_ID = 99;
    
    @InjectMocks
    @Autowired
    private UserService service;
    
    @Mock
    private UserRepository userRepository;
    
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldFindAllInvokeFindAllOnRepository() {
        service.findAll();
        
        Mockito.verify(userRepository).findAll();
    }
    
    @Test
    public void shouldFindOneWithIdInvokeFindOneOnRepository() {
        
        service.findOne(SAMPLE_ID);
        
        Mockito.verify(userRepository).findOne(SAMPLE_ID);
        
    }
}
