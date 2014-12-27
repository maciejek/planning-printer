package pl.wroc.pwr.agile.service;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.repository.EmployeeRepository;

@Service
@Transactional
@PersistenceContext(type = PersistenceContextType.EXTENDED)
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public void delete(Integer employeeId) {
        employeeRepository.delete(employeeId);
    }
}
