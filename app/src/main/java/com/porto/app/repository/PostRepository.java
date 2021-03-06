package com.porto.app.repository;

import androidx.lifecycle.LiveData;

import com.porto.app.dao.PostDao;
import com.porto.app.model.models.social.Post;
import com.porto.app.model.models.holder.PostHolder;

import java.util.List;

public class PostRepository {
    private PostDao postDao;

    private static PostRepository instance;
    private static Object lock = new Object();

    private PostRepository() {
        postDao = PostDao.getInstance();
    }

    public static PostRepository getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new PostRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<PostHolder>> getAllPosts() {
        return postDao.getAllPosts();
    }

    public void addPost(Post post) {
        postDao.addPost(post);
    }

    public PostHolder getPost(String postID) {
        if(!postID.equals("0"))
        return postDao.getPost(postID);
        else return new PostHolder();
    }
}
