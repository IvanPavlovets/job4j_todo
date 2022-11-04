package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Task;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

@AllArgsConstructor
public class TaskStoreTest {

    @Test
    public void whenAddTask() {
        SessionFactory sf = new Main().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(crudRepository);
        Task result = store.add(task);
        Task storedTask = store.findById(task.getId()).get();
        assertThat(storedTask, is(result));
    }

    @Test
    public void whenUpdateThenTrue() {
        SessionFactory sf = new Main().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(crudRepository);
        store.add(task);
        task.setDescription("descUpdate");
        store.update(task.getId(), task);
        Task storedTask = store.findById(task.getId()).get();
        assertThat(storedTask.getDescription(), is(task.getDescription()));
    }

    @Test
    public void whenCompleteThenTrue() {
        SessionFactory sf = new Main().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(crudRepository);
        store.add(task);
        store.complete(task.getId());
        boolean result = store.findById(task.getId()).get().isDone();
        assertTrue(result);
    }

    @Test
    public void whendeleteThenTrue() {
        SessionFactory sf = new Main().sf();
        CrudRepository crudRepository = new CrudRepository(sf);
        Task task = new Task();
        task.setDescription("desc1");
        TaskStore store = new TaskStore(crudRepository);
        store.add(task);
        store.delete(task.getId());
        Optional<Task> result = store.findById(task.getId());
        assertTrue(result.isEmpty());
    }

}
