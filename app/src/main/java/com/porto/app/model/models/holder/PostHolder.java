package com.porto.app.model.models.holder;

import com.porto.app.model.models.social.Post;
import com.porto.app.model.models.User;

import java.util.Objects;

public class PostHolder implements Comparable{
    private String postUID;
    private Post post;
    private User writtenBy;

    public PostHolder() {

    }

    public PostHolder(String postUID) {
        this.postUID = postUID;
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

    public User getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(User writtenBy) {
        this.writtenBy = writtenBy;
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
