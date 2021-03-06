package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(/*HttpServletRequest request, */Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private void authorizationDone(Map<String, Object> view) {
        view.put("message", "You have been authorized");
    }

    private void logoutDone(Map<String, Object> view) {
        view.put("message", "You have been logged out");
    }

    private void newsSubmitted(Map<String, Object> view) {
        view.put("message", "News have been submitted");
    }

    private void cantMakeNews(Map<String, Object> view) {
        view.put("message", "Should be authorized to make news");
    }
}