package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.CategoryStore;
import ru.job4j.todo.store.PriorityStore;
import ru.job4j.todo.store.TaskStore;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class TaskService {

    /**
     * Работа с БД через слой персистенции.
     */
    private final TaskStore taskStore;
    private final CategoryStore categoryStore;
    private final PriorityStore priorityStore;

    /**
     * Создание задания.
     * Метод добавляет task в БД
     *
     * @param task
     * @return Task
     */
    public Task addTask(Task task) {
        return taskStore.add(task);
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого task.
     *
     * @param task
     */
    public void updateTask(int id, Task task) {
        taskStore.update(id, task);
    }

    /**
     * Переводит task в состояние выполнено
     * путем замены флага в done, на true.
     *
     * @param id
     */
    public void completeTask(int id) {
        taskStore.complete(id);
    }

    /**
     * Удаление Task по id.
     *
     * @param id
     */
    public void deleteTask(int id) {
        taskStore.delete(id);
    }

    /**
     * Внутриний метод сдвига временой зоны.
     * @param tasks
     */
    private void shiftZone(List<Task> tasks, String zoneId) {
        tasks.forEach(
                task -> task.setCreated(
                        task.getCreated().atZone(TimeZone.getDefault().toZoneId())
                                .withZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime()
                )
        );
    }

    /**
     * Достает все task c флагом true
     *
     * @return List<Task>
     */
    public List<Task> findCompletedTask(String zoneId) {
        List<Task> tasks = taskStore.findCompleted();
        shiftZone(tasks, zoneId);
        return tasks;
    }

    /**
     * Достает все значения из хранилища (БД)
     *
     * @return List<Task>
     */
    public List<Task> findAllTasks(String zoneId) {
        List<Task> tasks = taskStore.findAll();
        shiftZone(tasks, zoneId);
        return tasks;
    }

    /**
     * Достает все task c флагом false
     *
     * @return List<Task>
     */
    public List<Task> findNotCompletedTask(String zoneId) {
        List<Task> tasks = taskStore.findNotCompleted();
        shiftZone(tasks, zoneId);
        return tasks;
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Task>
     */
    public Optional<Task> findTaskById(int id) {
        return taskStore.findById(id);
    }

    /**
     * Достает все Category из таблицы categories
     *
     * @return List<Category>
     */
    public List<Category> findAllCategories() {
        return categoryStore.findAllCategories();
    }

    /**
     * Находит category в БД по id
     *
     * @param id
     * @return Optional<Category>
     */
    public Optional<Category> findCategoryById(int id) {
        return categoryStore.findCategoryById(id);
    }

    /**
     * Находит Priority в БД по id
     *
     * @param id
     * @return Optional<Priority>
     */
    public Optional<Priority> findPriorityById(int id) {
        return priorityStore.findPriorityById(id);
    }

    /**
     * Достает все Priority из таблицы priorities
     *
     * @return
     */
    public List<Priority> findAllPriorities() {
        return priorityStore.findAllPriorities();
    }

}
