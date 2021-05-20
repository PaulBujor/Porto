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
import com.porto.app.model.Model;
import com.porto.app.model.models.social.Like;
import com.porto.app.model.models.holder.LikeHolder;
import com.porto.app.model.models.holder.PostHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LikeDao {
    private Map<String, MutableLiveData<List<LikeHolder>>> postLikes;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    private static LikeDao instance;
    private static Object lock = new Object();

    private LikeDao() {
        ref = database.getReference("porto-social-app-default-rtdb");

        postLikes = new HashMap<>();
        ref.child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Firebase Likes", snapshot.toString());
                List<LikeHolder> currentLikes = new ArrayList<>();
                try {
                    DataSnapshot subSnapshot;
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();

                    //iterate through all elements from posts tree
                    while (iterator.hasNext()) {
                        subSnapshot = iterator.next();
                        LikeHolder likeHolder = new LikeHolder(subSnapshot.getKey());

                        //keep reference to post as it is updated with data
                        Like like = new Like();
                        likeHolder.setLike(subSnapshot.getValue(Like.class));

                        currentLikes.add(likeHolder);
                    }
                } catch (Exception e) {
                    Log.e("Firebase deserialization", "This might be because there are no objects in the database");
                    Log.e("Firebase error", e.getStackTrace().toString());
                }

                for (String key : postLikes.keySet()) {
                    MutableLiveData<List<LikeHolder>> holder = postLikes.get(key);
                    holder.setValue(new ArrayList<LikeHolder>());
                }

                for (LikeHolder likeHolder : currentLikes) {
                    List<LikeHolder> likesForPost = checkPostExists(likeHolder.getLike().getPostId()).getValue();
                    likesForPost.add(likeHolder);
                    checkPostExists(likeHolder.getLike().getPostId()).setValue(likesForPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    public static LikeDao getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new LikeDao();
                }
            }
        }
        return instance;
    }

    private MutableLiveData<List<LikeHolder>> checkPostExists(PostHolder post) {
        return checkPostExists(post.getPostUID());
    }

    private MutableLiveData<List<LikeHolder>> checkPostExists(String postId) {
        MutableLiveData<List<LikeHolder>> likes = new MutableLiveData<>();
        likes.setValue(new ArrayList<>());
        postLikes.putIfAbsent(postId, likes);
        likes = postLikes.get(postId);

        return likes;
    }

    public LiveData<List<LikeHolder>> getLiveLikesOfPost(PostHolder post) {
        return checkPostExists(post);
    }

    public void addLike(Like like) {
        MutableLiveData<List<LikeHolder>> likes = checkPostExists(like.getPostId());
        List<LikeHolder> currentLikes = likes.getValue();

        boolean likeExists = false;
        Log.d("LikeDao", "Adding " + (like.getValue() == 1 ? "like" : "dislike") + " for post " + like.getPostId());
        for (LikeHolder tmpLike : currentLikes) {
            if (tmpLike.getLike().getUserId().equals(Model.getInstance().getCurrentUser().getUID())) {
                ref.child("likes").child(tmpLike.getLikeUID()).setValue(like);
                likeExists = true;
            }
        }
        if (!likeExists) {
            ref.child("likes").push().setValue(like);
        }

//        likes.setValue(currentLikes);
    }
}
