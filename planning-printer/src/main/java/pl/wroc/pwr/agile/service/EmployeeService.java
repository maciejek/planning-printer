package pl.wroc.pwr.agile.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserService userService;
    
    public Integer save(String name, String surname, EmployeeType type) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setType(type);
        employee.setWorkspace(userService.getLoggedUser().getWorkspace());
        employee = employeeRepository.save(employee);
        
        Integer employeeId = -1;
        
        if (employee.getId() != null) {
            employeeId = employee.getId();
        }
        return employeeId;
    }
    
    public void delete(Integer employeeId) {
        employeeRepository.delete(employeeId);
    }
}
