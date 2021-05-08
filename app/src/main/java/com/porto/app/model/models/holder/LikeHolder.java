package com.porto.app.model.models.holder;

import com.porto.app.model.models.Like;

import java.util.Objects;

public class LikeHolder {
    private String likeUID;
    private Like like;

    public LikeHolder() {
    }

    public LikeHolder(String likeUID, Like like) {
        this.likeUID = likeUID;
        this.like = like;
    }

    public LikeHolder(String likeUID) {
        this.likeUID = likeUID;
    }

    public String getLikeUID() {
        return likeUID;
    }

    public void setLikeUID(String likeUID) {
        this.likeUID = likeUID;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeHolder that = (LikeHolder) o;
        return Objects.equals(likeUID, that.likeUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeUID);
    }

    @Override
    public String toString() {
        return "/n/tLikeHolder{" +
                "likeUID='" + likeUID + '\'' +
                ", like=" + like +
                '}';
    }
}
