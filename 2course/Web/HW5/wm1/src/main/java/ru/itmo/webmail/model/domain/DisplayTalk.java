package ru.itmo.webmail.model.domain;

import java.util.Date;

public class DisplayTalk {
    private String sourceUser;
    private String targetUser;
    private String message;
    private Date creationTime;

    public String getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(String sourceUser) {
        this.sourceUser = sourceUser;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public DisplayTalk(String sourceUser, String targetUser, String message, Date creationTime) {
        this.sourceUser = sourceUser;
        this.targetUser = targetUser;
        this.message = message;
        this.creationTime = creationTime;
    }
}
