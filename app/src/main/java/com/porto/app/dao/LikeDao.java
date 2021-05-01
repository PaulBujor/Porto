package com.porto.app.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.porto.app.manager.Model;
import com.porto.app.model.Like;
import com.porto.app.model.Post;

import java.util.ArrayList;
import java.util.List;

public class LikeDao {
    private MutableLiveData<List<Like>> likes;

    private static LikeDao instance;
    private static Object lock = new Object();

    private LikeDao() {
        likes = new MutableLiveData<>();
        List<Like> newList = new ArrayList<>();
        likes.setValue(newList);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getScoreOfPost(Post post) {
        int score = 0;
        for (Like like : likes.getValue()) {
            if (post.equals(like.getOfPost()))
                score += like.getValue();
        }
        return score;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPostIsLiked(Post post) {
        for (Like like : likes.getValue()) {
            if (post.equals(like.getOfPost()) && like.getFromUser().equals(Model.getInstance().getCurrentUser()))
                return like.getValue();
        }
        return 0;
    }

    public void addLike(Like like) {
        List<Like> currentLikes = likes.getValue();
        boolean likeExists = false;
        for (Like tmpLike : currentLikes) {
            if (tmpLike.getFromUser().equals(Model.getInstance().getCurrentUser())) {
                tmpLike.setValue(like.getValue());
                likeExists = true;
            }
        }
        if (!likeExists)
            currentLikes.add(like);
        likes.setValue(currentLikes);
    }
}
