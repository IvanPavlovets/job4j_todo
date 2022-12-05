package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Слой персистенции для сущности Category
 */
@Repository
@AllArgsConstructor
public class CategoryStore {

    private final CrudRepository crudRepository;

    /**
     * Достает все значения из хранилища (БД)
     * @return List<Category>
     */
    public List<Category> findAllCategories() {
        return crudRepository.query(
                "from Category", Category.class
        );
    }

    /**
     * Находит запись в БД по id
     *
     * @param id
     * @return Optional<Category>
     */
    public Optional<Category> findCategoryById(int id) {
        Optional<Category> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from Category as с where с.id = :fId", Category.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }


}
