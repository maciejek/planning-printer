package pl.wroc.pwr.agile.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.repository.UserStoryRepository;

@Service
@Transactional
public class UserStoryService {

	@Autowired
	private UserStoryRepository userStoryRepository;
	
	public UserStory save(UserStory userStory){
		return userStoryRepository.save(userStory);
	}
	
	public void delete(Integer userStoryId){
		userStoryRepository.delete(userStoryId);
	}
	
	public UserStory addTasks(Integer storyId, ArrayList<Task> tasks){
		UserStory story = userStoryRepository.getOne(storyId);
		story.setTasks(tasks);
		
		return story;
	}
	
	public UserStory addTask(Integer storyId, Task task){
		UserStory story = userStoryRepository.getOne(storyId);
		if (story.getTasks() == null) {
			story.setTasks(new ArrayList<Task>());
		}
		story.getTasks().add(task);
		
		return story;
	}
	
	public UserStory getUserStoryById(Integer id){
		return userStoryRepository.findById(id);
	}
	
	
	public List<Task> getTasksByUserStory(Integer userStoryId){
		UserStory story = userStoryRepository.findById(userStoryId);
		return story.getTasks();
		
		
	}
}
