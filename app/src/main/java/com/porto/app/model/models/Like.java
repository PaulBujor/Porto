package com.porto.app.model.models;

import java.util.Objects;

public class Like {
    private String postId;
    private String userId;
    private int value;

    public Like() {
    }

    public Like(String postId, String userId, int value) {
        this.postId = postId;
        this.userId = userId;
        this.value = value;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(postId, like.postId) &&
                Objects.equals(userId, like.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }

    @Override
    public String toString() {
        return "\n\tLike{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", value=" + value +
                '}';
    }
}
