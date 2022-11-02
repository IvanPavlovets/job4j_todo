package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.todo.utils.UserUtils.getUserSession;


@Controller
@AllArgsConstructor
public class UserContoller {

    /**
     * Работа с БД через слой Service.
     */
    private final UserService userService;

    /**
     * Загружает страницу login.html
     * - Параметр fail создаеться для отработки
     * предупрежения alert в предсавлении
     * на тот случай когда вернеться
     * пустой Optional в условии.
     * @param model
     * @param fail
     * @return String
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Метод авторизации.
     * Делает обработку действий на странице login.html
     * В условии проверка на пустой Optional.
     * Из HttpServletRequest получаем обьект
     * HttpSession - текущая ссесия в данном браузере,
     * внутри используется ConcurrentHashMap, в котором
     * можно хранить текущего user, добовляем его в map
     * с помощью setAttribute().
     * При возврате пустого Optional у параметра fail
     * значение поменяеться на true и переход на loginPage.
     * @param user
     * @param req
     * @return String
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByLoginAndPassword(
                user.getLogin(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/all";
    }

    /**
     * Обработки нажатия ссылки "Выход"
     * Удаляет все данные связанные с текущем пользователем
     * и завершает текущую сессию.
     * @param session
     * @return String
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

    /**
     * загружает страницу addUser.html.
     * - Параметр fail создаеться для отработки
     * предупрежения alert в предсавлении
     * на тот случай когда вернеться
     * пустой Optional в условии.
     * @param model
     * @return String
     */
    @GetMapping("/formAddUser")
    public String addUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("user", new User());
        return "addUser";
    }

    /**
     * Метод регистрации впервые.
     * Обрабатывает добавление данных
     * из полеей ввода в обьект user и
     * и последующеее сохранение в UserStore.
     * В условии проверка на пустой Optional
     * - При возврате пустого Optional у параметра fail
     * значение поменяеться на true и переход на formAddUser.
     * @param user
     * @return String
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = userService.addUser(user);
        if (regUser.isEmpty()) {
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/loginPage";
    }

    /**
     * Обрабатывает переход на страницу personalInfo.html
     * @param model
     * @param session
     * @return String
     */
    @GetMapping("/personlInfo")
    public String personlInfo(Model model, HttpSession session) {
        User user = getUserSession(session);
        model.addAttribute("user", user);
        return "personlInfo";
    }

    /**
     * Метод перехода на обновления карточки
     * пользователя. Передаються параметры
     * выбранные ранее name, login, password.
     * @param fail
     * @return String
     */
    @GetMapping("/formUpdateUser")
    public String formUpdateUser(Model model, HttpSession session,
                                 @RequestParam(name = "fail", required = false) Boolean fail) {
        User user = getUserSession(session);
        model.addAttribute("fail", fail != null);
        model.addAttribute("user", user);
        model.addAttribute("editUser", user);
        return "updateUser";
    }

    /**
     * Метод обработки изменений в карточки пользователя
     * @param user
     * @param req
     * @return String
     */
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, HttpServletRequest req) {
        if (!userService.updateUser(user.getId(), user)) {
            return "redirect:/formUpdateUser?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userService.findById(user.getId()).get());
        return "redirect:/personlInfo";
    }

}
