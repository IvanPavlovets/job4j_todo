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
        return Optional.ofNullable(user);
    }

    /**
     * Находит запись в БД по условию.
     * Возвращает найденую запись или пустой Optional.
     * @param name
     * @param login
     * @return Optional<User>
     */
    public Optional<User> findUserByNameAndLogin(String name, String login) {
        Optional<User> result;
        try {
            Session session = sf.openSession();
            session.beginTransaction();
            result = session.createQuery(
                    "from User as u WHERE"
                            + " u.name = :fName AND"
                            + " u.login = :fLogin")
                    .setParameter("fName", name)
                    .setParameter("fLogin", login)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return result;
    }

        /**
         * Обновляет запись в БД.
         * Поля старой записи по id меняеться на
         * поля из переданого user.
         * @param id
         * @param task
         * @return boolean
         */
        public boolean update(int id, User user) {
            String updateQuery = "UPDATE User as u SET"
                    + " u.name = :fName"
                    + " u.password = fPassword"
                    + " WHERE u.id = :fId";
            Session session = sf.openSession();
            session.beginTransaction();
            int rsl = session.createQuery(updateQuery)
                    .setParameter("fName", user.getName())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
            return rsl > 0;
        }

}
