package com.porto.app.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.porto.app.model.Post;
import com.porto.app.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class PostDao {
    private MutableLiveData<List<Post>> posts;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("porto-social-app-default-rtdb");

    private static PostDao instance;
    private static Object lock = new Object();

    private PostDao() {
        posts = new MutableLiveData<>();
        posts.setValue(new ArrayList<>());
        ref.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Post> currentPosts = posts.getValue();
                currentPosts.add(snapshot.getValue(Post.class));
                posts.setValue(currentPosts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    public static PostDao getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PostDao();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Post>> getAllPosts() {
        return posts;
    }

    public void addPost(Post post) {
        DatabaseReference postsRef = ref.child("posts");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(post);

//        List<Post> currentPosts = posts.getValue();
//        currentPosts.add(post);
//        posts.setValue(currentPosts);
    }
}
