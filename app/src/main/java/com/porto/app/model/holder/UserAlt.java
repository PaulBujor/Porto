package com.porto.app.model.holder;

import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class UserAlt {
    private String UID;
    private MutableLiveData<String> username;

    public UserAlt(MutableLiveData<String> username) {
        this.username = username;
    }

    public UserAlt() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAlt userAlt = (UserAlt) o;
        return Objects.equals(UID, userAlt.UID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID);
    }
}
