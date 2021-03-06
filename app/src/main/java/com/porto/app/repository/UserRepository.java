package com.porto.app.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.porto.app.dao.UserDao;

public class UserRepository {
    private UserDao userDao;

    private static UserRepository instance;
    private static Object lock = new Object();

    private UserRepository() {
        userDao = UserDao.getInstance();
    }

    public static UserRepository getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public MutableLiveData<String> getUsername(String UID) {
        return userDao.getUsername(UID);
    }

    public void registerUser(FirebaseUser firebaseUser, String username) {
        userDao.registerUser(firebaseUser, username);
    }

}
