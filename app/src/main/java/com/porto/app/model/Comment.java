package com.porto.app.model;

import java.time.LocalDateTime;

public class Comment {
    private Post forPost;
    private User fromUser;
    private LocalDateTime timestamp;
    private String message;

    public Comment() {
    }

    public Comment(Post forPost, User fromUser, LocalDateTime timestamp, String message) {
        this.forPost = forPost;
        this.fromUser = fromUser;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Post getForPost() {
        return forPost;
    }

    public void setForPost(Post forPost) {
        this.forPost = forPost;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
