package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {

    private final CrudRepository crudRepository;

    /**
     * Добавляет User в бд.
     * На поле login ноложено ограничение
     * UNIQUE, при добавлении дубликата login
     * в бд, возникнет исключение.
     *
     * @param user
     * @return Optional<User>
     */
    public Optional<User> add(User user) {
        try {
            crudRepository.run(
                    session -> session.persist(user));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(user);
    }

    /**
     * Находит запись в БД по условию.
     * Возвращает найденую запись или пустой Optional.
     *
     * @param password
     * @param login
     * @return Optional<User>
     */
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Optional<User> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from User as u WHERE"
                            + " u.login = :fLogin AND"
                            + " u.password = :fPassword", User.class,
                    Map.of("fLogin", login, "fPassword", password));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого user.
     *
     * @param id
     * @param user
     * @return Optional<User>
     */
    public boolean update(int id, User user) {
        boolean rsl = false;
        try {
            return crudRepository.tx(
                    session -> session.createQuery("UPDATE User as u SET"
                            + " u.name = :fName,"
                            + " u.login = :fLogin,"
                            + " u.password = :fPassword"
                            + " WHERE u.id = :fId")
                            .setParameter("fName", user.getName())
                            .setParameter("fLogin", user.getLogin())
                            .setParameter("fPassword", user.getPassword())
                            .setParameter("fId", id)
                            .executeUpdate() > 0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }


    /**
     * Поиск пользователя по id.
     *
     * @param id
     * @return Optional<User>
     */
    public Optional<User> findById(int id) {
        Optional<User> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional(
                    "from User as u where u.id = :fId", User.class,
                    Map.of("fId", id));
            return rsl;
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
        }
    }

}
