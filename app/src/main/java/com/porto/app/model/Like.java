package com.porto.app.model;

public class Like {
    private Post ofPost;
    private User fromUser;
    private int value;

    public Like() {
    }

    public Like(Post ofPost, User fromUser, int value) {
        this.ofPost = ofPost;
        this.fromUser = fromUser;
        this.value = value;
    }

    public Post getOfPost() {
        return ofPost;
    }

    public void setOfPost(Post ofPost) {
        this.ofPost = ofPost;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
