package com.porto.app.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.porto.app.model.Model;
import com.porto.app.model.models.Like;
import com.porto.app.model.models.holder.PostHolder;

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
