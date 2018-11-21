package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        try {
            getUserService().validateEnter(loginOrEmail, password);
        } catch (ValidationException e) {
            display(view, loginOrEmail, password, e.getMessage());
            return;
        }

        User user = getUserService().authorize(loginOrEmail, password);
        if (!user.isConfirmed()) {
            display(view, loginOrEmail, password, "Your registration has not been confirmed");
            return;
        }
        getEventService().addEnter(user);
        request.getSession(true).setAttribute(USER_ID_SESSION_KEY, user.getId());

        throw new RedirectException("/index");
    }

    private void display(Map<String, Object> view, String loginOrEmail, String password, String errorMessage) {
        view.put("loginOrEmail", loginOrEmail);
        view.put("password", password);
        view.put("error", errorMessage);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
