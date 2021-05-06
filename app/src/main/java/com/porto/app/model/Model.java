package com.porto.app.model;

import com.google.firebase.auth.FirebaseUser;
import com.porto.app.model.models.User;
import com.porto.app.repository.UserRepository;

public class Model {
    //Singleton
    private static Model instance;
    private static Object threadLock = new Object();

    public static Model getInstance() {
        if(instance == null) {
            synchronized (threadLock) {
                if(instance == null) {
                    instance = new Model();
                }
            }
        }
        return instance;
    }

    private User currentUser;

    private Model() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        currentUser = new User(firebaseUser.getUid(), UserRepository.getInstance().getUsername(firebaseUser.getUid()));
    }
}
