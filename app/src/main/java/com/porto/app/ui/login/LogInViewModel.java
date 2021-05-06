package com.porto.app.ui.login;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.porto.app.dao.UserDao;
import com.porto.app.manager.Model;

public class LogInViewModel extends ViewModel {
    public void logInUser(FirebaseUser user) {
        Model.getInstance().setFirebaseUser(user);
    }
}