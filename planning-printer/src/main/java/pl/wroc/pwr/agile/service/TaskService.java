package pl.wroc.pwr.agile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.repository.TaskRepository;

public class TaskService {

	
	@Autowired
	private TaskRepository taskRepository;
	
	public List<Task> findAllTasks(){
		return taskRepository.findAll();
	}
	
	public Task getTaskById(Integer id){
		return taskRepository.findOne(id);
	}
	
	public void addTask(Task task){
		taskRepository.save(task);
	}
	
	public void deleteTaskById(Integer id){
		taskRepository.delete(id);
	}
	
	
}
