package pl.wroc.pwr.agile.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.controller.UserController;
import pl.wroc.pwr.agile.entity.Employee;
import pl.wroc.pwr.agile.entity.EmployeeType;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.WorkspaceRepository;

@Service
@Transactional
public class WorkspaceService {
    
	Logger logger = LoggerFactory.getLogger(UserController.class);

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
    
    
    public List<UserStory> findAllUserStories(int workspaceId){
    	List<UserStory> userStories = workspaceRepository.findOne(workspaceId).getUserStories();
    	if (userStories == null) {
			return new ArrayList<UserStory>();
		}
    	else{
    		logger.warn(" ilosc taskow "+userStories.get(0).getTasks().size());
    		return userStories;
    	}
    }
    
    
    
    
    
    
    
}
