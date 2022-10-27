package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TaskStoreTest {
    static SessionFactory sf;

    @BeforeClass
    public static void beforeClass() {
        sf = new Main().sf();
    }

    @AfterClass
    public static void closeSf() {
        sf.close();
    }

    @After
    public void clearDB() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete Task").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddTask() {
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(sf);
        Task result = store.add(task);
        assertThat(store.findById(task.getId()), is(result));
    }

    @Test
    public void whenUpdateThenTrue() {
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(sf);
        store.add(task);
        task.setDescription("descUpdate");
        boolean result = store.update(task.getId(), task);
        assertTrue(result);
    }

    @Test
    public void whenCompleteThenTrue() {
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(sf);
        store.add(task);
        store.complete(task.getId());
        boolean result = store.findById(task.getId()).isDone();
        assertTrue(result);
    }

    @Test
    public void whendeleteThenTrue() {
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(sf);
        store.add(task);
        boolean result = store.delete(task.getId());
        assertTrue(result);
    }

}
