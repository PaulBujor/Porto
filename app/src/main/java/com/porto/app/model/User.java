package com.porto.app.model;

import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class User {
    //private int profileImageId; //todo
    private String UID;
    private MutableLiveData<String> username = new MutableLiveData<>();

    public User() {
        username = new MutableLiveData<>();
    }

    public User(String UID, MutableLiveData<String> username) {
        this.UID = UID;
        this.username = username;
    }

    public User(String UID, String username) {
        this.UID = UID;
        this.username.setValue(username);
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(MutableLiveData<String> username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
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
        return Objects.hash(UID);
    }
}
