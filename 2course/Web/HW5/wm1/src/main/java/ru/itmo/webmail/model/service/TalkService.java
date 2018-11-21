package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.ArrayList;
import java.util.TreeSet;

public class TalkService {
    private TalkRepository talkRepository = new TalkRepositoryImpl();

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public ArrayList<Talk> findBySourceUserId(long sourceUserId) {
        return talkRepository.findBySourceUserId(sourceUserId);
    }

    public ArrayList<Talk> findByTargetUserId(long targetUserId) {
        return talkRepository.findByTargetUserId(targetUserId);
    }

    public ArrayList<Talk> findById(long userId) {
        return talkRepository.findById(userId);
    }
}
