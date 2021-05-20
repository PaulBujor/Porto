package com.porto.app.repository;

import androidx.lifecycle.LiveData;

import com.porto.app.dao.LikeDao;
import com.porto.app.model.models.social.Like;
import com.porto.app.model.models.holder.LikeHolder;
import com.porto.app.model.models.holder.PostHolder;

import java.util.List;

public class LikeRepository {
    private LikeDao likeDao;

    private static LikeRepository instance;
    private static Object lock = new Object();

    private LikeRepository() {
        likeDao = LikeDao.getInstance();
    }

    public static LikeRepository getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new LikeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<LikeHolder>> getLiveLikesOfPost(PostHolder post) {
        return likeDao.getLiveLikesOfPost(post);
    }

    public void addLike(Like like) {
        likeDao.addLike(like);
    }
}
