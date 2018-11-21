package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.EmailConfirmation;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");
        if (secret == null) {
            view.put("message", "No confirmation secret");
            return;
        }

        EmailConfirmation emailConfirmation = getEmailConfirmationService().findBySecret(secret);
        if (emailConfirmation == null || !secret.equals(emailConfirmation.getSecret())) {
            view.put("message", "Incorrect confirmation secret");
            return;
        }

        getUserService().confirm(emailConfirmation.getUserId());
        view.put("message", "You have been confirmed");
    }
}
