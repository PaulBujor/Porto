package com.porto.app.ui.register;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.porto.app.model.Model;
import com.porto.app.repository.UserRepository;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterViewModel extends ViewModel {
    public void registerUser(FirebaseUser user, String username) {
        UserRepository.getInstance().registerUser(user, username);
        Model.getInstance().getCurrentUser().getUsername().setValue(username);
    }
}