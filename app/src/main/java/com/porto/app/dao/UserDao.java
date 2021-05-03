package com.porto.app.dao;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.porto.app.model.User;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserDao {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("porto-social-app-default-rtdb");

    private static UserDao instance;
    private static Object lock = new Object();

    private UserDao(){}

    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new UserDao();
                }
            }
        }
        return instance;
    }

    public MutableLiveData<String> getUserName(String UID) {
        MutableLiveData<String> username = new MutableLiveData<>();
        ref.child("users").child(UID).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                username.setValue(String.valueOf(task.getResult().getValue()));
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
            }
        });
        Log.i("Username", "Received username " + username.getValue());
        return username;
    }

    public void registerUser(FirebaseUser firebaseUser, String username) {
        User user = new User();
        user.setUsername(username);
        user.setUID(firebaseUser.getUid());
        ref.child("users").child(user.getUID()).setValue(user.getUsername());
    }
}
