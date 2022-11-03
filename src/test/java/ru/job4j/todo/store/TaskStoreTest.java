package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Task;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TaskStoreTest {

    public CrudRepository crudRepository;

//    public TaskStoreTest() {
//        crudRepository = new CrudRepository();
//    }

//    @Test
//    public void whenAddTask() {
//        Task task = new Task();
//        task.setDescription("desc1");
//        TaskStore store = new TaskStore(crudRepository);
//        Task result = store.add(task);
//        assertThat(store.findById(task.getId()), is(result));
//    }
//
//    @Test
//    public void whenUpdateThenTrue() {
//        Task task = new Task();
//        task.setDescription("desc1");
//        TaskStore store = new TaskStore(crudRepository);
//        store.add(task);
//        task.setDescription("descUpdate");
//        boolean result = store.update(task.getId(), task);
//        assertTrue(result);
//    }
//
//    @Test
//    public void whenCompleteThenTrue() {
//        Task task = new Task();
//        task.setDescription("desc1");
//        TaskStore store = new TaskStore(crudRepository);
//        store.add(task);
//        store.complete(task.getId());
//        boolean result = store.findById(task.getId()).isDone();
//        assertTrue(result);
//    }

//    @Test
//    public void whendeleteThenTrue() {
//        Task task = new Task();
//        task.setDescription("desc1");
//        TaskStore store = new TaskStore(crudRepository);
//        store.add(task);
//        store.delete(task.getId());
//        Optional<Task> result = store.findById(task.getId());
//        assertNull(result);
//    }

}
