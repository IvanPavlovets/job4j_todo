package todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TaskStore {
    private final SessionFactory sf;

    public TaskStore(SessionFactory sf) {
        this.sf = sf;
    }
}
