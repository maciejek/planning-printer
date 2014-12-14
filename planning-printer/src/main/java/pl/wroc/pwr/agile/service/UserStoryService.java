package pl.wroc.pwr.agile.service;

import java.util.ArrayList;

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
		story.getTasks().add(task);
		
		return story;
	}
}
