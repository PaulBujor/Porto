package com.porto.app.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.porto.app.model.Post;
import com.porto.app.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class PostDao {
    private MutableLiveData<List<Post>> posts;

    private static PostDao instance;
    private static Object lock = new Object();

    private PostDao() {
        posts = new MutableLiveData<>();
        List<Post> newList = new ArrayList<>();
        posts.setValue(newList);
    }

    public static PostDao getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
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
        List<Post> currentPosts = posts.getValue();
        currentPosts.add(post);
        posts.setValue(currentPosts);
    }
}
