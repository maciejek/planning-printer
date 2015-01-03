package pl.wroc.pwr.agile.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

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
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.UserStoryRepository;

/**
 * 
 * @author wposlednicka
 *
 */

public class UserStoryServiceTest {
	
 	private static final int USER_STORY_ID = 99;
	private static final String NUMBER = "US01-test";
	private static final String POINTS = "5";
	private static final String SUMMARY = "Testowy opis user story";
	
	@Mock
	private UserStory userStory;
	
	@InjectMocks
	@Autowired
	private UserStoryService userStoryService;
	
	@Mock
	private UserStoryRepository storyRepository;	
	
	@Mock
	private Workspace workSpace;
	
	@Mock
	private UserService userService;
	
	@Mock
	private User user;
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
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
		when(storyRepository.findOne(userStory.getId())).thenReturn(userStory);
		UserStory userStoryById = userStoryService.getUserStoryById(userStory.getId());
		Assert.assertEquals(userStory.getId(), userStoryById.getId());
		userStoryService.delete(userStory.getId());
	}
	
	@Test
	public void shouldFindTasksByUserStoryId(){
		userStoryService.save(userStory);
		when(storyRepository.findOne(userStory.getId())).thenReturn(userStory);
		Collection<Task> tasksByUserStoryId = userStoryService.getTasksByUserStoryId(userStory.getId());
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
