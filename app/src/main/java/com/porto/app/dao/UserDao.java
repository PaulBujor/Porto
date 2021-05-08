package com.porto.app.dao;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.porto.app.model.models.User;

public class UserDao {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    private static UserDao instance;
    private static Object lock = new Object();

    private UserDao(){
        ref = database.getReference("porto-social-app-default-rtdb");
    }

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

    public MutableLiveData<String> getUsername(String UID) {
        MutableLiveData<String> username = new MutableLiveData<>();
        ref.child("users").child(UID).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                username.setValue(String.valueOf(task.getResult().getValue()));
                Log.d("firebase username", String.valueOf(task.getResult().getValue()));
            }
        });
        return username;
    }

    public void registerUser(FirebaseUser firebaseUser, String username) {
        User user = new User(firebaseUser.getUid(), username);
        user.setUsername(username);
        user.setUID(firebaseUser.getUid());
        ref.child("users").child(user.getUID()).setValue(user.getUsername().getValue());
    }
}
