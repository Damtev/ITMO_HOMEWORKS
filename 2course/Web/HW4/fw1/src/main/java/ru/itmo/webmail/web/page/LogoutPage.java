package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;

public class LogoutPage extends Page {
    public void action(HttpServletRequest request) {
        request.getSession().removeAttribute("AuthorizedUser");
        throw new RedirectException("index", "logoutDone");
    }
}
