package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

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


}
