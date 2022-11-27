package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.CategoryStore;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    /**
     * Работа с БД через слой персистенции.
     */
    private final TaskStore taskStore;
    private final CategoryStore categoryStore;

    /**
     * Создание задания.
     * Метод добавляет task в БД
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
     * @param task
     */
    public void updateTask(int id, Task task) {
        taskStore.update(id, task);
    }

    /**
     * Переводит task в состояние выполнено
     * путем замены флага в done, на true.
     * @param id
     */
    public void completeTask(int id) {
        taskStore.complete(id);
    }

    /**
     * Удаление Task по id.
     * @param id
     */
    public void deleteTask(int id) {
        taskStore.delete(id);
    }

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Task>
     */
    public List<Task> findAllTasks() {
        return taskStore.findAll();
    }

    /**
     * Достает все task c флагом true
     * @return List<Task>
     */
    public List<Task> findCompletedTask() {
        return taskStore.findCompleted();
    }

    /**
     * Достает все task c флагом false
     * @return List<Task>
     */
    public List<Task> findNotCompletedTask() {
        return taskStore.findNotCompleted();
    }

    /**
     * Находит запись в БД по id
     * @param id
     * @return Optional<Task>
     */
    public Optional<Task> findTaskById(int id) {
        return taskStore.findById(id);
    }

    /**
     * Достает все Category из таблицы categories
     * @return List<Category>
     */
    public List<Category> findAllCategories() {
        return categoryStore.findAllCategories();
    }
}
