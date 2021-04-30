package com.porto.app.model;

import java.time.LocalDateTime;

public class Post {
    private User writtenBy;
    private String text;
    private LocalDateTime timestamp;

    public Post() {
    }

    public Post(User writtenBy, String text) {
        this.writtenBy = writtenBy;
        this.text = text;
    }

    public Post(String text) {
        this.text = text;
    }

    public User getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(User writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }
}
