package com.porto.app.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.porto.app.manager.Model;
import com.porto.app.model.Post;
import com.porto.app.model.User;
import com.porto.app.model.holder.PostHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
