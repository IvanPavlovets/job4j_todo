package ru.job4j.todo.utils;

import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

public final class UserUtils {

    private UserUtils() {
    }

    /**
     * Утилитарный метод получения текущего
     * user по текущей session.
     * @param session
     * @return User
     */
    public static User getUserSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
