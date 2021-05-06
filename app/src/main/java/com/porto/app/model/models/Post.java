package com.porto.app.model.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Post implements Comparable {
    private String writtenBy;
    private String text;
    private long timestamp;

    public Post() {
    }

    public Post(String text) {
        this.text = text;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
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

    @Override
    public int compareTo(Object o) {
        Post post = (Post) o;
        return Long.compare(timestamp, post.timestamp);
    }
}
