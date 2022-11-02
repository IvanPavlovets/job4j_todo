package ru.job4j.todo.utils;

import lombok.experimental.UtilityClass;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

@UtilityClass
public class UserUtils {

    /**
     * Утилитарный метод получения текущего
     * user по текущей session.
     * @param session
     * @return User
     */
    public User getUserSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
