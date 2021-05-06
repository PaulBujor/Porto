package com.porto.app.dao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.porto.app.manager.Model;
import com.porto.app.model.Like;
import com.porto.app.model.Post;
import com.porto.app.model.holder.PostHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikeDao {
    private MutableLiveData<List<Like>> likes;
    private Map<String, MutableLiveData<List<Like>>> postLikes;

    private static LikeDao instance;
    private static Object lock = new Object();

    private LikeDao() {
        postLikes = new HashMap<>();
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

    private MutableLiveData<List<Like>> checkPostExists(PostHolder post) {
        MutableLiveData<List<Like>> likes = new MutableLiveData<>();
        likes.setValue(new ArrayList<>());
        postLikes.putIfAbsent(post.getPostUID(), likes);

        return likes;
    }

    private MutableLiveData<List<Like>> checkPostExists(String postId) {
        MutableLiveData<List<Like>> likes = new MutableLiveData<>();
        likes.setValue(new ArrayList<>());
        postLikes.putIfAbsent(postId, likes);
        likes = postLikes.get(postId);

        return likes;
    }

    public LiveData<List<Like>> getLiveLikesOfPost(PostHolder post) {
        return checkPostExists(post);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getScoreOfPost(PostHolder post) {
        int score = 0;
        try {
            for (Like like : likes.getValue()) {
                if (post.getPostUID().equals(like.getPostId()))
                    score += like.getValue();
            }
        } catch (NullPointerException e) {
            Log.d("LikeDao", "No likes for post " + post.getPostUID());
        }
        Log.d("LikeDao", "Score of post " + post.getPostUID() + ": " + score);
        return score;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPostIsLiked(PostHolder post) {
        try {
            for (Like like : likes.getValue()) {
                if (post.getPostUID().equals(like.getPostId()) && like.getUserId().equals(Model.getInstance().getCurrentUser().getUID())) {
                    Log.d("LikeDao", "Post " + post.getPostUID() + ": Like by " + Model.getInstance().getCurrentUser().getUID());
                    return like.getValue();
                }
            }
        } catch (NullPointerException e) {
            Log.d("LikeDao", "No likes for post " + post.getPostUID());
        }
        return 0;
    }

    public void addLike(Like like) {
        MutableLiveData<List<Like>> likes = checkPostExists(like.getPostId());
        List<Like> currentLikes = likes.getValue();

        boolean likeExists = false;
        Log.d("LikeDao", "Adding " + (like.getValue() == 1 ? "like" : "dislike") + " for post " + like.getPostId());
        for (Like tmpLike : currentLikes) {
            if (tmpLike.getUserId().equals(Model.getInstance().getCurrentUser().getUID())) {
                tmpLike.setValue(like.getValue());
                likeExists = true;
            }
        }
        if (!likeExists)
            currentLikes.add(like);

        likes.setValue(currentLikes);
    }
}
