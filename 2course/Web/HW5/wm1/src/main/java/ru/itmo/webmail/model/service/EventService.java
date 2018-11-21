package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private EventRepository eventRepository = new EventRepositoryImpl();

    public void addEnter(User user) {
        addEvent(user, Event.Type.ENTER);
    }

    public void addLogout(User user) {
        addEvent(user, Event.Type.LOGOUT);
    }

    private void addEvent(User user, Event.Type type) {
        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(type);
        eventRepository.save(event);
    }

}
