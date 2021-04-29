package com.porto.app.model;

public class User {
    private long userId;
    private int profileImageId;
    private String username;
    private String name;

    public User() {
    }

    public User(int profileImageId, String username, String name) {
        this.profileImageId = profileImageId;
        this.username = username;
        this.name = name;
    }

    public int getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(int profileImageId) {
        this.profileImageId = profileImageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }
}
