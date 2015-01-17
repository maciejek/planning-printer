package pl.wroc.pwr.agile.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.TaskType;
import pl.wroc.pwr.agile.entity.User;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.TaskRepository;

public class TaskServiceTest {
	
	private static final int TASK_ID = 99;
	private static final String NUMBER = "T01-test";
	private static final String POINTS = "3";
	private static final String SUMMARY = "Testowy task";
	
	@Mock
	private Task task;
	
    @Mock
    private List<Task> tasks;
	
	@InjectMocks
	@Autowired
	private TaskService taskService;
	
	@Mock
	private Workspace workSpace;
	
	@Mock
	private UserService userService;
	
	@Mock
	private User user;
	
	@Mock
	private TaskRepository taskRepository;
	
	@Mock
	private UserStory userStory;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldInvokeFindAllOnRepository() {
		taskService.findAllTasks();
		verify(taskRepository).findAll();
	}
	
	@Test
	public void shouldInvokeFindOneOnRepository() {
		taskService.getTaskById(TASK_ID);
		verify(taskRepository).findOne(TASK_ID);
	}
	
	@Test
	public void shouldInvokeDeleteOnRepository() {
		taskService.deleteTaskById(task.getId());
		verify(taskRepository).delete(task.getId());
	}
	
	@Test
	public void shoudInvokeSaveOnRepository() {
        taskService.saveTask(task);
        verify(taskRepository).save(task);
        taskService.saveTasks(tasks);
        verify(taskRepository).save(tasks);
	}

    @Test
    public void shouldSaveTask() {
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        Task task = getDummyTask();
        task.setId(TASK_ID);
        
        when(taskRepository.save(Matchers.any(Task.class))).thenReturn(task);
        
        taskService.save(NUMBER, POINTS, SUMMARY, TaskType.DEVELOPER_TASK, userStory);

        verify(taskRepository).save(taskCaptor.capture());

        assertThat(taskCaptor.getValue().getNumber(), is(NUMBER));
        assertThat(taskCaptor.getValue().getEstimation(), is(Double.parseDouble(POINTS)));
        assertThat(taskCaptor.getValue().getSummary(), is(SUMMARY));
    }
    
    private Task getDummyTask() {
        Task task = new Task();
        task.setNumber(NUMBER);
        task.setEstimation(Double.parseDouble(POINTS));
        task.setSummary(SUMMARY);
        task.setType(TaskType.DEVELOPER_TASK);
        return task;
    }
}
