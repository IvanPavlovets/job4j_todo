package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

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
     *
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
     *
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
     *
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
     *
     * @param model
     * @return String
     */
    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession session) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        model.addAttribute("task", new Task());
        model.addAttribute("categories", taskService.findAllCategories());
        model.addAttribute("priorities", taskService.findAllPriorities());
        return "addTask";
    }

    /**
     * Обрабатывает добавление данных в task
     * category и user
     * и их сохранение в store.
     *
     * @param task
     * @return String
     */
    @PostMapping("/createTask")
    public String createTask(@RequestParam("categoryId") List<Integer> categoryId,
                             @RequestParam("priorityId") int priorityId,
                             @ModelAttribute Task task, HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        for (Integer id : categoryId) {
            var category = taskService.findCategoryById(id)
                    .orElseThrow(() -> {
                        return new NoSuchElementException("Category not found with id" + id);
                    });
            task.getCategories().add(category);
        }
        var priority = taskService.findPriorityById(priorityId)
                .orElseThrow(() -> {
                    return new NoSuchElementException("Priority not found with id" + priorityId);
                });
        task.setPriority(priority);
        taskService.addTask(task);
        return "redirect:/all";
    }

    /**
     * Обработка действий книпки удалить.
     * Удаление, выбраного, task по id.
     *
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
     *
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
     *
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
     *
     * @param id id выбраного task
     * @return String
     */
    @PostMapping("/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") int id) {
        taskService.completeTask(id);
        return "redirect:/all";
    }

}
