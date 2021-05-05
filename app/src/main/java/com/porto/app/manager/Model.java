package com.porto.app.manager;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.porto.app.dao.UserDao;
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
        MutableLiveData<String> currentUserUsername = new MutableLiveData<>();
        currentUserUsername.setValue("");
        this.firebaseUser = firebaseUser;
        currentUser = new User();
        currentUser.setUID(firebaseUser.getUid());

        currentUserUsername = UserDao.getInstance().getUserName(currentUser.getUID());
        currentUserUsername.observeForever((username) -> {
            currentUser.setUsername(username);
        });
    }
}
