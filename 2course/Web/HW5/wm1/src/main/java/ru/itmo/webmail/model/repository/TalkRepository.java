package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;

public interface TalkRepository {
    void save(Talk talk);

    ArrayList<Talk> findBySourceUserId(long sourceUserId);

    ArrayList<Talk> findByTargetUserId(long targetUserId);

    ArrayList<Talk> findById(long userId);
}
