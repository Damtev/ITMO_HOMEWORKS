package ru.itmo.wp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class MessageServlet extends HttpServlet {

    private TreeMap<String, HashSet<String> > sessions;
//    HttpSession
    private String curUser;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authType = request.getAuthType();
        HttpSession session = request.getSession();
        session.
        if (authType.equals("message/auth")) {
            String user = request.getHeader("user");
            if (user != null) {
                curUser = user;
                if (!sessions.containsKey(user)) {
                    sessions.put(user, new HashSet<>());
                }
            } else {
                user = "";
            }
        }
    }
}
