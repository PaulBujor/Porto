package com.porto.app.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post implements Comparable {
    private User writtenBy;
    private String text;
    private long timestamp;

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

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return writtenBy.equals(post.writtenBy) &&
                text.equals(post.text) &&
                timestamp == post.timestamp;
    }


    @Override
    public int hashCode() {
        return Objects.hash(writtenBy, text, timestamp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(Object o) {
        Post post = (Post) o;
        return Long.compare(timestamp, post.timestamp);
    }
}
