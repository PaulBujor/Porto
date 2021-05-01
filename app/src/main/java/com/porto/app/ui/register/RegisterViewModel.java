package com.porto.app.ui.register;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public void register(String username, String email, String password, String passwordConfirm) throws Exception {
        AtomicBoolean isSuccesful = new AtomicBoolean(false);
        if(passwordConfirm.equals(password)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    isSuccesful.set(true);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(logInTask -> {
                        if (logInTask.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build());
                        }
                        FirebaseAuth.getInstance().signOut();
                    });
                } else {
                    Log.i("Register", task.getException().getMessage());
                }
            });
        }
        if(!isSuccesful.get())
            throw new Exception("Something went wrong");
    }
}