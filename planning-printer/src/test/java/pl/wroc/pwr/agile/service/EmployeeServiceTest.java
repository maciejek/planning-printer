package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.repository.EmployeeRepository;

public class EmployeeServiceTest {
    
    private static final Integer SAMPLE_ID = 99;
    private static final String SAMPLE_NAME = "James";
    private static final String SAMPLE_SURNAME = "Bond";
    
    @InjectMocks
    @Autowired
    private EmployeeService employeeService;
    
    @Mock
    private EmployeeRepository employeeRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private Employee employee;
    
    @Mock
    private User user;
    
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldSaveEmployee() {
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        
        when(employeeRepository.save(Matchers.any(Employee.class))).thenReturn(employee);
        when(userService.getLoggedUser()).thenReturn(user);
        
        employeeService.save(SAMPLE_NAME, SAMPLE_SURNAME, EmployeeType.DEVELOPER);
        
        verify(employeeRepository).save(employeeCaptor.capture());
        
        assertThat(employeeCaptor.getValue().getName(), is(SAMPLE_NAME));
        assertThat(employeeCaptor.getValue().getSurname(), is(SAMPLE_SURNAME));
        assertThat(employeeCaptor.getValue().getType(), is(EmployeeType.DEVELOPER));
    }
    
    @Test
    public void shouldDeleteWithIdInvokeDeleteOnRepository() {
        employeeService.delete(SAMPLE_ID);
        verify(employeeRepository).delete(SAMPLE_ID);
    }

}
