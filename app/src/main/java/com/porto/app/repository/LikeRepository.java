package com.porto.app.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.porto.app.dao.LikeDao;
import com.porto.app.dao.PostDao;
import com.porto.app.manager.Model;
import com.porto.app.model.Like;
import com.porto.app.model.Post;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getScoreOfPost(Post post) {
        return likeDao.getScoreOfPost(post);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPostIsLiked(Post post) {
        return likeDao.getPostIsLiked(post);
    }

    public void addLike(Like like) {
        likeDao.addLike(like);
    }
}
