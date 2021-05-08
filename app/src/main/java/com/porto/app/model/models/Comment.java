package com.porto.app.model.models;

import java.time.LocalDateTime;

public class Comment {
    private String forPost;
    private String fromUser;
    private String message;

    public Comment() {
    }

    public Comment(String forPost, String fromUser, String message) {
        this.forPost = forPost;
        this.fromUser = fromUser;
        this.message = message;
    }

    public String getForPost() {
        return forPost;
    }

    public void setForPost(String forPost) {
        this.forPost = forPost;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
