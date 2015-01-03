package pl.wroc.pwr.agile.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wroc.pwr.agile.entity.Task;
import pl.wroc.pwr.agile.entity.UserStory;
import pl.wroc.pwr.agile.entity.Workspace;
import pl.wroc.pwr.agile.repository.TaskRepository;
import pl.wroc.pwr.agile.repository.UserStoryRepository;
import pl.wroc.pwr.agile.service.UserService;
import pl.wroc.pwr.agile.service.UserStoryService;
import pl.wroc.pwr.agile.service.WorkspaceService;

@Controller
public class UserStoryController {
    private static Logger logger = LoggerFactory.getLogger(UserStoryController.class);

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private UserStoryService userStoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private TaskRepository taskRepository;

    @ModelAttribute("task")
    public Task construct() {
        return new Task();
    }

    @RequestMapping("/story")
    @Transactional(readOnly = true)
    public String showStory(Model model) {
    	
        Workspace workspace = workspaceService.getCurrentWorkspace();
        Collection<UserStory> userStories = workspace.getUserStories();

        logger.info("wika" + workspace.toString());

        for (UserStory story : userStories) {
            System.out.println(story.getSummary());

            Collection<Task> tasks = story.getTasks();

            for (Task task : tasks) {
                System.out.println(task.getSummary());
            }

            System.out.println();
        }
        
        //TODO posortować userStories

        if (!userStories.isEmpty()) {
            model.addAttribute("stories", userStories);
        }
        return "story";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addTask(@Valid @ModelAttribute("task") Task task,
            BindingResult result, @RequestParam Integer storyId) {
        if (result.hasErrors()) {
            return "story";
        }

        UserStory userStoryById = userStoryService.getUserStoryById(storyId);
        logger.info(userStoryById.getSummary());
        task.setUserStory(userStoryById);
        taskRepository.save(task);

        return "redirect:/story.html?success=true";
    }

}
