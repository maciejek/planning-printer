package pl.wroc.pwr.agile.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
@Transactional
public class WorkspaceService {
    
    @Autowired
    private WorkspaceRepository workspaceRepository;
    
    public void save(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    public List<Workspace> findAll() {
        return workspaceRepository.findAll();
    }
    
    public List<Employee> findDevelopersInWorkspace(int workspaceId) {
        List<Employee> developers = new LinkedList<Employee>();
        List<Employee> employees = workspaceRepository.findOne(workspaceId).getEmployees();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.DEVELOPER) {
                developers.add(employee);
            }
        }
        return developers;
    }
    
    public List<Employee> findTestersInWorkspace(int workspaceId) {
        List<Employee> testers = new LinkedList<Employee>();
        List<Employee> employees = workspaceRepository.findOne(workspaceId).getEmployees();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.TESTER) {
                testers.add(employee);
            }
        }
        return testers;
    }
}
