package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class TaskService {

    /**
     * Работа с БД через слой персистенции.
     */
    private final TaskStore taskStore;

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
    public boolean updateTask(Integer id, Task task) {
        return taskStore.update(id, task);
    }

    /**
     * Переводит task в состояние выполнено
     * путем замены флага в done, на true.
     * @param id
     */
    public boolean completeTask(Integer id) {
        return taskStore.complete(id);
    }

    /**
     * Удаление Task по id.
     * @param id
     * @return
     */
    public boolean deleteTask(Integer id) {
        return taskStore.delete(id);
    }

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Task>
     */
    public List<Task> findAllTasks() {
        return taskStore.findAll();
    }

    /**
     * Находит запись в БД по id
     * @param id
     * @return Task
     */
    public Task findTaskById(int id) {
        return taskStore.findById(id);
    }
}
