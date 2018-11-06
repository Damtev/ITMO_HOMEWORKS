package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {

    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            User authorized = userService.authorize(login, password);
            request.getSession().setAttribute("AuthorizedUser", authorized);
            throw new RedirectException("/index", "authorizationDone");
        } catch (ValidationException error) {
            view.put("login", login);
            view.put("password", password);
            view.put("error", error.getMessage());
        }

    }

    private void action() {
        // Do nothing
    }
}
