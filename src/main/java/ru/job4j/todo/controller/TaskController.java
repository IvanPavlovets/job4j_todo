package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

import static ru.job4j.todo.utils.UserUtils.getUserSession;


@Controller
@AllArgsConstructor
public class TaskController {
    /**
     * Работа с TaskStore через промежуточный слой TaskService
     */
    private final TaskService taskService;

    /**
     * Обрабатывает переход на all.html
     * Используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде.
     * @return String
     */
    @GetMapping("/all")
    public String all(Model model, HttpSession session) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findAllTasks());
        return "all";
    }

    /**
     * Обрабатывает переход на done.html
     * @param model
     * @return String
     */
    @GetMapping("/done")
    public String done(Model model, HttpSession session) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findCompletedTask());
        return "done";
    }

    /**
     * Обрабатывает переход на notDone.html
     * @param model
     * @return String
     */
    @GetMapping("/notDone")
    public String notDone(Model model, HttpSession session) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskService.findNotCompletedTask());
        return "notDone";
    }

    /**
     * Обрабатывает переход на addTask.html
     * @param model
     * @return String
     */
    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession session) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("task", new Task());
        return "addTask";
    }

    /**
     * Обрабатывает добавление данных в task
     * и их сохранение в store.
     * @param task
     * @return String
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        taskService.addTask(task);
        return "redirect:/all";
    }

    /**
     * Обработка действий книпки удалить.
     * Удаление, выбраного, task по id.
     * @param id id выбраного task
     * @return String
     */
    @PostMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        taskService.deleteTask(id);
        return "redirect:/all";
    }

    @GetMapping("/detailed/{taskId}")
    public String detailed(Model model, HttpSession session,
                       @PathVariable("taskId") int id) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("task", taskService.findTaskById(id).get());
        return "detailed";
    }

    /**
     * Обрабатывает переход на edit.html
     * @param model
     * @param id
     * @return String
     */
    @GetMapping("/formEdit/{taskId}")
    public String edit(Model model, HttpSession session,
                       @PathVariable("taskId") int id) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("task", taskService.findTaskById(id));
        return "edit";
    }

    /**
     * Обрабатывает действие на сервере
     * редактирование task на edit.html
     * @param task
     * @return String
     */
    @PostMapping("/editTask")
    public String edit(@ModelAttribute Task task) {
        taskService.updateTask(task.getId(), task);
        return "redirect:/detailed/" + task.getId();
    }

    /**
     * Обработка действий книпки завершить.
     * Переводит task в состояние выполнено
     * путем замены флага в done, на true.
     * @param id id выбраного task
     * @return String
     */
    @PostMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") int id) {
        taskService.completeTask(id);
        return "redirect:/all";
    }

}
