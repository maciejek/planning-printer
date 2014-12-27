package pl.wroc.pwr.agile.service;

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
	
	public UserStory getUserStoryById(Integer id){
		return userStoryRepository.findById(id);
	}
	
	public List<Task> getTasksByUserStoryId(Integer userStoryId){
		UserStory story = userStoryRepository.findById(userStoryId);
		return story.getTasks();
	}
	
	public List<UserStory> findAllUserStory(){
		return userStoryRepository.findAll();
	}
}
