package pl.wroc.pwr.agile.service;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.UserStoryRepository;

/**
 * 
 * @author wposlednicka
 *
 */
@Ignore
public class UserStoryServiceTest {
	
 	private static final int USER_STORY_ID = 99;
	private static final int TASK1_ID = 1;
	private static final int TASK2_ID = 2;
	private static final String NUMBER = "US01-test";
	private static final String POINTS = "5";
	private static final String SUMMARY = "Testowy opis user story";
	private UserStory userStory;
	private Task task1;
	private Task task2;
	private List<Task> tasksList;
	
	@InjectMocks
	@Autowired
	private UserStoryService userStoryService;
	
	@Mock
	private UserStoryRepository storyRepository;	
	
	@Autowired
	private Workspace workSpace;
	
	@Mock
	private UserService userService;
	
	@Mock
	private User user;
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		createFakeUserStory();
		createFakeTasks();
		setTasksList();
	}

	private void setTasksList() {
		userStory.setTasks(tasksList);
	}

	private void createFakeTasks() {
		task1 = new Task();
		task1.setId(TASK1_ID); 
		task1.setEstimation(2.0);
		task1.setSummary("opis tasku 1");
		task1.setType(TaskType.DEVELOPER_TASK);
		task1.setUserStory(userStory);
		
		task2 = new Task();
		task2.setId(TASK2_ID); 
		task2.setEstimation(4.0);
		task2.setSummary("opis tasku 2");
		task2.setType(TaskType.TESTER_TASK);
		task2.setUserStory(userStory);
		
		tasksList = new ArrayList<Task>();
		tasksList.add(task1);
		tasksList.add(task2);
	}

	private void createFakeUserStory() {
		userStory = new UserStory();
		userStory.setId(USER_STORY_ID);
		userStory.setNumber(NUMBER);
		userStory.setPoints(POINTS);
		userStory.setSummary(SUMMARY);
		userStory.setWorkspace(workSpace);
	}

	@Test
	public void shouldFindAllInvokeFindAllOnRepository(){
		userStoryService.findAllUserStory();
		Mockito.verify(storyRepository).findAll();
	}
	
	@Test
	public void shouldFindOneWithIdInvokeFindAllOnRepository(){
		userStoryService.getUserStoryById(USER_STORY_ID);
		Mockito.verify(storyRepository).findOne(USER_STORY_ID);
	}
	 
	@Test
	public void shouldFindUserStoryById(){
		userStoryService.save(userStory);
		UserStory userStoryById = userStoryService.getUserStoryById(USER_STORY_ID);
		Assert.assertEquals(userStory, userStoryById);
		userStoryService.delete(userStory.getId());
	}
	
	@Test
	public void shouldFindTasksByUserStoryId(){
		userStoryService.save(userStory);
		List<Task> tasksByUserStoryId = userStoryService.getTasksByUserStoryId(USER_STORY_ID);
		Assert.assertEquals(userStory.getTasks().size(), tasksByUserStoryId.size());
		userStoryService.delete(userStory.getId());
	}
	
	@Test
	public void shouldSaveStory(){
		 ArgumentCaptor<UserStory> storyCaptor = ArgumentCaptor.forClass(UserStory.class);
		 when(storyRepository.save(Matchers.any(UserStory.class))).thenReturn(userStory);
		 when(userService.getLoggedUser()).thenReturn(user);
		 userStoryService.save(NUMBER, POINTS, SUMMARY);
		 verify(storyRepository).save(storyCaptor.capture());
		 
		 assertThat(storyCaptor.getValue().getNumber(), is(NUMBER));
	     assertThat(storyCaptor.getValue().getPoints(), is(POINTS));
	     assertThat(storyCaptor.getValue().getSummary(),is(SUMMARY));
	}
	
	@Test
	public void shouldDeleteStory(){
		userStoryService.delete(USER_STORY_ID);
		verify(storyRepository).delete(USER_STORY_ID);
	}
	
	
}
