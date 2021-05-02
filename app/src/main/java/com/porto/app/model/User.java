package com.porto.app.model;

import java.util.Objects;

public class User {
    //private int profileImageId; //todo
    private String UID;
    private String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

//    public int getProfileImageId() {
//        return profileImageId;
//    }
//
//    public void setProfileImageId(int profileImageId) {
//        this.profileImageId = profileImageId;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return UID.equals(user.UID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
