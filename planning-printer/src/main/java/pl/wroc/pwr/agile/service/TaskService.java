package pl.wroc.pwr.agile.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.repository.TaskRepository;

@Service
@Transactional
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	
	
	public List<Task> findAllTasks(){
		return taskRepository.findAll();
	}
	
	public Task getTaskById(Integer id){
		return taskRepository.findOne(id);
	}
	
	public void saveTask(Task task){
		taskRepository.save(task);
	}
	
	public void deleteTaskById(Integer id){
		taskRepository.delete(id);
	}
	
	public void saveTasks(List<Task> tasks) {
	    taskRepository.save(tasks);
	}
	
	public void save(String number, String estimation, String summary, TaskType taskType, UserStory userStory) {
	    Task task = new Task();
        task.setNumber(number);
        task.setUserStory(userStory);
        task.setEstimation(Double.parseDouble(estimation));
        task.setSummary(summary);
        task.setComplete(false);
        task.setType(taskType);
        saveTask(task);
    }
	
}
