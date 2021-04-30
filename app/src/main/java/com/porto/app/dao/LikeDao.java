package com.porto.app.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.porto.app.model.Like;
import com.porto.app.model.Post;

import java.util.ArrayList;
import java.util.List;

public class LikeDao {
    private MutableLiveData<List<Like>> posts;

    private static LikeDao instance;
    private static Object lock = new Object();

    private LikeDao() {
        posts = new MutableLiveData<>();
        List<Like> newList = new ArrayList<>();
        posts.setValue(newList);
    }

    public static LikeDao getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new LikeDao();
                }
            }
        }
        return instance;
    }


}
