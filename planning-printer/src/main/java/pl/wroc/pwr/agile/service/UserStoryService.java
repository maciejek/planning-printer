package pl.wroc.pwr.agile.service;

import java.util.Collection;
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

    @Autowired
    private UserService userService;

    public UserStory save(UserStory userStory) {
        return userStoryRepository.save(userStory);
    }

    public void delete(Integer userStoryId) {
        userStoryRepository.delete(userStoryId);
    }

    public UserStory getUserStoryById(Integer id) {
        return userStoryRepository.findOne(id);
    }

    public Collection<Task> getTasksByUserStoryId(Integer userStoryId) {
        UserStory story = userStoryRepository.findOne(userStoryId);
        return story.getTasks();
    }

    public List<UserStory> findAllUserStory() {
        return userStoryRepository.findAll();
    }

    public Integer save(String number, String points, String summary) {
        UserStory userStory = new UserStory();
        userStory.setNumber(number);
        userStory.setPoints(points);
        userStory.setSummary(summary);
        userStory.setComplete(false);
        userStory.setTasks(new java.util.HashSet<Task>());
        userStory.setWorkspace(userService.getLoggedUser().getWorkspace());
        userStory = userStoryRepository.save(userStory);

        Integer userStoryId = -1;

        if (userStory.getId() != null) {
            userStoryId = userStory.getId();
        }
        return userStoryId;
    }

    public void edit(Integer id, String number, String points, String summary) {
        UserStory storyToEdit = getUserStoryById(id);
        storyToEdit.setNumber(number);
        storyToEdit.setPoints(points);
        storyToEdit.setSummary(summary);
        save(storyToEdit);
    }
}
