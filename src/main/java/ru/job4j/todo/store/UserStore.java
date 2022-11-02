package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {
    private final SessionFactory sf;

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
            Session session = sf.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
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
        Optional<User> result = Optional.empty();
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            result = session.createQuery(
                    "from User as u WHERE"
                            + " u.login = :fLogin AND"
                            + " u.password = :fPassword")
                    .setParameter("fLogin", login)
                    .setParameter("fPassword", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * Обновляет запись в БД.
     * Поля старой записи по id меняеться на
     * поля из переданого user.
     *
     * @param id
     * @param user
     * @return boolean
     */
    public boolean update(int id, User user) {
        boolean rsl = false;
        try {
            String updateQuery = "UPDATE User as u SET"
                    + " u.name = :fName,"
                    + " u.login = :fLogin,"
                    + " u.password = :fPassword"
                    + " WHERE u.id = :fId";
            Session session = sf.openSession();
            session.beginTransaction();
            rsl = session.createQuery(updateQuery)
                    .setParameter("fName", user.getName())
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            return rsl;
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
        Session session = sf.openSession();
        session.beginTransaction();
        Optional<User> result = Optional.ofNullable(session.get(User.class, id));
        session.getTransaction().commit();
        session.close();
        return result;
    }
}