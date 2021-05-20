package com.porto.app.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.porto.app.model.models.social.Comment;
import com.porto.app.model.models.holder.PostHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CommentDao {
    private Map<String, MutableLiveData<List<Comment>>> postComments;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    private static CommentDao instance;
    private static Object lock = new Object();

    public CommentDao() {
        ref = database.getReference("porto-social-app-default-rtdb");

        postComments = new HashMap<>();
        ref.child("comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Comment> currentComments = new ArrayList<>();

                try {
                    DataSnapshot subSnapshot;
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();

                    //iterate through all elements from posts tree
                    while (iterator.hasNext()) {
                        subSnapshot = iterator.next();
                        currentComments.add(subSnapshot.getValue(Comment.class));
                    }
                } catch (Exception e) {
                    Log.e("Firebase deserialization", "This might be because there are no objects in the database");
                    Log.e("Firebase error", e.getStackTrace().toString());
                }

                for (String key : postComments.keySet()) {
                    MutableLiveData<List<Comment>> reset = postComments.get(key);
                    reset.setValue(new ArrayList<Comment>());
                }

                for (Comment comment : currentComments) {
                    List<Comment> likesForPost = checkPostExists(comment.getForPost()).getValue();
                    likesForPost.add(comment);
                    checkPostExists(comment.getForPost()).setValue(likesForPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private MutableLiveData<List<Comment>> checkPostExists(PostHolder post) {
        return checkPostExists(post.getPostUID());
    }

    private MutableLiveData<List<Comment>> checkPostExists(String postId) {
        MutableLiveData<List<Comment>> likes = new MutableLiveData<>();
        likes.setValue(new ArrayList<>());
        postComments.putIfAbsent(postId, likes);
        likes = postComments.get(postId);

        return likes;
    }

    public static CommentDao getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new CommentDao();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Comment>> getCommentsOfPost(String postID) {
        return checkPostExists(postID);
    }

    public void addComment(Comment comment) {
        ref.child("comments").push().setValue(comment);
    }
}
