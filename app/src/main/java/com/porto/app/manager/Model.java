package com.porto.app.manager;

import androidx.lifecycle.MutableLiveData;

import com.porto.app.model.Post;

import java.util.List;

public interface Model {
    public MutableLiveData<List<Post>> getAllPosts();
}
