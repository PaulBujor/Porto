package com.porto.app.model.holder;

import com.porto.app.model.Post;

import java.util.Objects;

public class PostHolder {
    private String postUID;
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
}
