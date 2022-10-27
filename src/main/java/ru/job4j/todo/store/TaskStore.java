package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;

    /**
     * Создание задания.
     * Метод добавляет task в БД
     * @param task
     * @return Task
     */
    public Task add(Task task) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого task.
     * @param id
     * @param task
     * @return boolean
     */
    public boolean update(int id, Task task) {
        String updateQuery = "UPDATE Task as t SET"
                + " t.description = :fDescription"
                + " WHERE t.id = :fId";
        Session session = sf.openSession();
        session.beginTransaction();
        int rsl = session.createQuery(updateQuery)
                .setParameter("fDescription", task.getDescription())
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return rsl > 0;
    }

    /**
     * Переводит task в состояние выполнено
     * путем обнавления записи в БД,
     * меняет флаг done на true.
     * Обновляет запись в БД.
     * @param id
     * @return boolean
     */
    public boolean complete(int id) {
        String updateQuery = "UPDATE Task as t SET"
                + " t.done = :fDone"
                + " WHERE t.id = :fId";
        Session session = sf.openSession();
        session.beginTransaction();
        int rsl = session.createQuery(updateQuery)
                .setParameter("fDone", true)
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return rsl > 0;
    }

    /**
     * Удаление Task по id.
     * @param id
     * @return boolean
     */
    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int rsl = session.createQuery(
                "DELETE Task WHERE id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return rsl > 0;
    }

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Task>
     */
    public List<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> tasks = session.createQuery(
                "from Task").list();
        session.getTransaction().commit();
        session.close();
        return tasks;
    }

    /**
     * Достает все task c флагом true
     * @return List<Task>
     */
    public List<Task> findCompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> completed = session.createQuery(
                "from Task as t WHERE"
                        + " t.done = :fDone")
                .setParameter("fDone", true)
                .list();
        session.getTransaction().commit();
        session.close();
        return completed;
    }

    /**
     * Достает все task c флагом false
     * @return List<Task>
     */
    public List<Task> findNotCompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> notCompleted = session.createQuery(
                "from Task as t WHERE"
                        + " t.done = :fDone")
                .setParameter("fDone", false)
                .list();
        session.getTransaction().commit();
        session.close();
        return notCompleted;
    }

    /**
     * Находит запись в БД по id
     * @param id
     * @return Task
     */
    public Task findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

}
