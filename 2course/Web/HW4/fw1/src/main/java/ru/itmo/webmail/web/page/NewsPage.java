package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.NewsService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class NewsPage extends Page {

    private NewsService newsService = new NewsService();

    private void submit(HttpServletRequest request) throws ServletException {
        User authorizedUser = (User) request.getSession().getAttribute("AuthorizedUser");
        if (authorizedUser == null) {
            throw new ServletException("Should be authorized to make news");
        }
        String text = request.getParameter("news-text");
        if (text == null) {
            throw new ServletException("News text can't be empty");
        }
        newsService.makeNews(authorizedUser, text);

        throw new RedirectException("index", "newsSubmitted");
    }

    private void action() {
        // Do nothing
    }
}
