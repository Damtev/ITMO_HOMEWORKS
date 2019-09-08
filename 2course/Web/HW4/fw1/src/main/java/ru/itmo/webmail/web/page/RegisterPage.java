package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterPage extends Page {
    private UserService userService = new UserService();

    private void register(HttpServletRequest request, Map<String, Object> view) throws ServletException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            userService.validateRegistration(user, password, confirmPassword);
        } catch (ValidationException e) {
            view.put("login", user.getLogin());
            view.put("email", user.getEmail());
            view.put("password", password);
            view.put("error", e.getMessage());
            return;
        }

        if (userService.findAll().isEmpty()) {
            user.setId(1);
        } else {
            user.setId(userService.findAll().get(userService.findAll().size() - 1).getId() + 1);
        }
        userService.register(user, user.getEmail(), password);

        try {
            User authorized = userService.authorize(user.getLogin(), password);
            request.getSession().setAttribute("AuthorizedUser", authorized);
        } catch (ValidationException e) {
            throw new ServletException("Authorization problems", e);
        }
        throw new RedirectException("/index", "registrationDone");
    }

    private void action(/*HttpServletRequest request, Map<String, Object> view*/) {
        // No operations.
    }
}
