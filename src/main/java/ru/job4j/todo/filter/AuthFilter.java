package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Класс отвечает за то что только авторизованный
 * пользователь (с именем, почтой и телефоном) может
 * покупать билеты. Даные пользователя необходимы для
 * формирования обьекта билета - Ticket.
 */
@Component
public class AuthFilter implements Filter {

    /**
     * Set совпадений uri
     */
    private Set<String> uris = Set.of("loginPage", "login", "formAddUser", "registration");

    /**
     * Через этот метод будут проходить запросы к сервлетам.
     * - Если запрос идет к адресам loginPage или login,
     * то мы их пропускаем сразу - возможность залогинеться.
     * - Если запросы идут к другим адресам, то проверяем
     * наличие пользователя в HttpSessio - тут только для авторизированых
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uris.stream().anyMatch(s -> uri.endsWith(s))) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }
}
