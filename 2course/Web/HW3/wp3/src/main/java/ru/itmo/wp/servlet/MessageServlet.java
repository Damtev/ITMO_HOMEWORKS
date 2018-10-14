package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class MessageServlet extends HttpServlet {

    private ArrayList<Message> messages = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        String action = request.getRequestURI().split("/")[2];
        String user;
        switch (action) {
            case "auth":
                user = request.getParameter("user");
                if (user != null) {
                    session.setAttribute("user", user);
                } else {
                    user = "";
                }
                response.getWriter().print(gson.toJson(user));
                break;
            case "findAll":
                response.getWriter().print(gson.toJson(messages));
                break;
            case "add":
                user = (String) session.getAttribute("user");
                String text = request.getParameter("text");
                messages.add(new Message(user, text));
        }
        response.getWriter().flush();
    }

    private class Message {
        private String user;
        private String text;

        private Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}