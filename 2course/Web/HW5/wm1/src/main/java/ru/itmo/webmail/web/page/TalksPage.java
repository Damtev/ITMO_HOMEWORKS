package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.DisplayTalk;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.TalkService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TalksPage extends Page {
    private TalkService talkService = new TalkService();

    private TalkService getTalkService() {
        return talkService;
    }

    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        User user = getUser();
//        ArrayList<Talk> bySource = getTalkService().findBySourceUserId(user.getId());
//        ArrayList<Talk> byTarget = getTalkService().findByTargetUserId(user.getId());
//        List<Talk> talks = Stream.concat(bySource.stream(), byTarget.stream())
//                .sorted(Comparator.comparing(Talk::getCreationTime)).collect(Collectors.toList());
        List<Talk> talks = getTalkService().findById(user.getId());
        talks.sort(Comparator.comparing(Talk::getCreationTime));
        List<DisplayTalk> displayTalks = new ArrayList<>();
        for (Talk talk : talks) {
            User source = getUserService().find(talk.getSourceUserId());
            User target = getUserService().find(talk.getTargetUserId());
            String message = talk.getText();
            displayTalks.add(new DisplayTalk(source.getLogin(), target.getLogin(), message, talk.getCreationTime()));
        }
        view.put("talks", displayTalks);
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    public void sendMessage(HttpServletRequest request, Map<String, Object> view) throws UnsupportedEncodingException, ValidationException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String messageText = request.getParameter("message-text");
        String targetUserLogin = request.getParameter("targetUser");
        User targetUser = getUserService().findByLogin(targetUserLogin);
        if (targetUser == null || !targetUser.isConfirmed()) {
            view.put("message", String.format("User '%s' does not exist", targetUserLogin));
            return;
        }
        if (messageText == null) {
            view.put("message", "Message can't be empty");
            return;
        }
        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(targetUser.getId());
        talk.setText(messageText);
        getTalkService().save(talk);
//        view.put("message", "Message was sent");
        throw new RedirectException("/talks");
    }
}
