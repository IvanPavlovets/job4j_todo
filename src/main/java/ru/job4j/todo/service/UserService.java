package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    /**
     * Работа с БД через слой персистенции.
     */
    private final UserStore userStore;

    /**
     * Найти запись в БД по условию или пустой Optional
     * @param login
     * @param password
     * @return Optional<User>
     */
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userStore.findUserByLoginAndPassword(login, password);
    }

    /**
     * Добавляет usera в userStore
     * @param user
     * @return Optional<User>
     */
    public Optional<User> addUser(User user) {
        return userStore.add(user);
    }

    /**
     * Заменить запись во внутренем хранилище
     * на вновь переданую в аргументе.
     * @param id
     * @param user
     * @return boolean
     */
    public boolean updateUser(int id, User user) {
        return userStore.update(id, user);
    }

    /**
     * Поиск по id
     * @param id
     * @return Optional<User>
     */
    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

}
