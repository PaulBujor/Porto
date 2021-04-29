package com.porto.app.model;

public class Post {
    private long postId;
    private User writtenBy;
    private String text;
    private int score;

    public Post() {
    }

    public Post(User writtenBy, String text, int score) {
        this.writtenBy = writtenBy;
        this.text = text;
        this.score = score;
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

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getPostId() {
        return postId;
    }
}
