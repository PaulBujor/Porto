package com.porto.app.repository;

import androidx.lifecycle.LiveData;

import com.porto.app.dao.LikeDao;
import com.porto.app.dao.PostDao;
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
}
