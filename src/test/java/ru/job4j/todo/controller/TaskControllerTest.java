package ru.job4j.todo.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Test
    public void whenAllGET() {
        Task task1 = new Task();
        task1.setDescription("desc1");
        Task task2 = new Task();
        task2.setDescription("desc2");
        List<Task> tasks = Arrays.asList(task1, task2);
        Model model = mock(Model.class);
        TaskService taskService = mock(TaskService.class);
        HttpSession session = mock(HttpSession.class);
        when(taskService.findAllTasks()).thenReturn(tasks);
        TaskController taskController = new TaskController(taskService);
        String page = taskController.all(model, session);
        verify(model).addAttribute("tasks", tasks);
        assertThat(page, is("all"));
    }
}
