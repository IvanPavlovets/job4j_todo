package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import ru.job4j.todo.model.Priority;
import java.util.Map;
import java.util.Optional;

/**
 * Слой персистенции для сущности Priority
 */
@Repository
@AllArgsConstructor
public class PriorityStore {

    private final CrudRepository crudRepository;

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Priority>
     */
    public List<Priority> findAllPriorities() {
        return crudRepository.query(
                "from Priority", Priority.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Priority>
     */
    public Optional<Priority> findPriorityById(int id) {
        Optional<Priority> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Priority as p where p.id = :fId", Priority.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

}
