package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskStore {

    private final CrudRepository crudRepository;

    /**
     * Создание задания.
     * Метод добавляет task в БД
     *
     * @param task
     * @return Task
     */
    public Task add(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого task.
     *
     * @param id
     * @param task
     */
    public void update(int id, Task task) {
        crudRepository.run(
                "UPDATE Task as t SET"
                        + " t.description = :fDescription"
                        + " WHERE t.id = :fId",
                Map.of("fDescription", task.getDescription(), "fId", id)
        );
    }

    /**
     * Переводит task в состояние выполнено
     * путем обнавления записи в БД,
     * меняет флаг done на true.
     * Обновляет запись в БД.
     *
     * @param id
     */
    public void complete(int id) {
        crudRepository.run(
                "UPDATE Task as t SET"
                        + " t.done = :fDone"
                        + " WHERE t.id = :fId",
                Map.of("fDone", true, "fId", id)
        );
    }

    /**
     * Удаление Task по id.
     *
     * @param id
     */
    public void delete(int id) {
        crudRepository.run(
                "DELETE Task WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    /**
     * Достает все значения из хранилища (БД)
     *
     * @return List<Task>
     */
    public List<Task> findAll() {
        return crudRepository.query(
                "from Task", Task.class
        );
    }

    /**
     * Достает все task c флагом true
     *
     * @return List<Task>
     */
    public List<Task> findCompleted() {
        return crudRepository.query(
                "from Task as t WHERE"
                        + " t.done = :fDone", Task.class,
                Map.of("fDone", true)
        );
    }

    /**
     * Достает все task c флагом false
     *
     * @return List<Task>
     */
    public List<Task> findNotCompleted() {
        return crudRepository.query(
                "from Task as t WHERE"
                        + " t.done = :fDone", Task.class,
                Map.of("fDone", false)
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Task>
     */
    public Optional<Task> findById(int id) {
        Optional<Task> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Task as t where t.id = :fId", Task.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

}
