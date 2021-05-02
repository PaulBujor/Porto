package com.porto.app.manager;

import com.google.firebase.auth.FirebaseUser;
import com.porto.app.model.User;

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
    private FirebaseUser firebaseUser;

    private Model() {
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
        currentUser = new User();
        currentUser.setUsername(firebaseUser.getDisplayName());
        currentUser.setUID(firebaseUser.getUid());
    }
}
