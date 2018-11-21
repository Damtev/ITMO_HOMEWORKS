package ru.itmo.webmail.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static ru.itmo.webmail.web.page.Page.USER_ID_SESSION_KEY;

public class EnterFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Long userId = (Long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        if (userId == null) {
            response.sendRedirect("/enter");
            return;
        }
        chain.doFilter(request, response);
    }
}
