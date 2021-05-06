package com.porto.app.ui.login;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.porto.app.model.Model;

public class LogInViewModel extends ViewModel {
    public void logInUser(FirebaseUser user) {
        Model.getInstance().setFirebaseUser(user);
    }
}