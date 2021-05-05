package com.porto.app.model.holder;

import com.porto.app.model.Post;
import com.porto.app.model.User;

import java.util.Objects;

public class PostHolder implements Comparable{
    private String postUID;
    private UserAlt altUser;
    private Post post;

    public PostHolder() {

    }

    public PostHolder(String postUID, Post post) {
        this.postUID = postUID;
        this.post = post;
    }

    public String getPostUID() {
        return postUID;
    }

    public void setPostUID(String postUID) {
        this.postUID = postUID;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserAlt getAltUser() {
        return altUser;
    }

    public void setAltUser(UserAlt altUser) {
        this.altUser = altUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostHolder that = (PostHolder) o;
        return Objects.equals(postUID, that.postUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postUID);
    }

    @Override
    public int compareTo(Object o) {
        PostHolder post = (PostHolder) o;
        return Long.compare(this.post.getTimestamp(), post.post.getTimestamp());
    }
}
