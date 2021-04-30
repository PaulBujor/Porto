package com.porto.app.model;

public class User {
    private int profileImageId; //todo
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public int getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(int profileImageId) {
        this.profileImageId = profileImageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
