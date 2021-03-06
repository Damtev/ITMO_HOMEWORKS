package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!isGet(request) || isFavicon(request) || isCaptchaPassed(request) || isCorrectAnswer(request)) {
            chain.doFilter(request, response);
            return;
        }

        String expectedAnswer = generateText();
        request.getSession().setAttribute("Expected-Answer", expectedAnswer);
        sendCaptcha(response, expectedAnswer);
    }

    private boolean isGet(HttpServletRequest request) {
        return request.getMethod().equals("GET");
    }

    private boolean isCaptchaPassed(HttpServletRequest request) {
        return request.getSession().getAttribute("Captcha-Passed") != null &&
                (Boolean) request.getSession().getAttribute("Captcha-Passed");
    }

    private boolean isFavicon(HttpServletRequest request) {
        return request.getRequestURI().endsWith("favicon.ico");
    }

    private boolean isCorrectAnswer(HttpServletRequest request) {
        if (request.getParameter("answer") != null &&
                request.getParameter("answer").equals(request.getSession().getAttribute("Expected-Answer"))) {
            request.getSession().setAttribute("Captcha-Passed", true);
            return true;
        }
        return false;
    }

    private String generateText() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000));
    }

    private void sendCaptcha(HttpServletResponse response, String expectedAnswer) throws IOException {
        response.setContentType("text/html");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.print(makeImageTag(expectedAnswer));
        Files.copy(Paths.get(getServletContext().getRealPath("static"), "captcha-form.html"), outputStream);
        outputStream.flush();
    }

    private String makeImageTag(String expectedAnswer) {
        String captcha = Base64.getEncoder().encodeToString(ImageUtils.toPng(expectedAnswer));
        return String.format("<img src=\"data:image/png;base64,%s\">\n", captcha);
    }
}
