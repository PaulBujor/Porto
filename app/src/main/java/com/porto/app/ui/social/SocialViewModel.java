package com.porto.app.ui.social;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.porto.app.manager.Model;
import com.porto.app.manager.ModelManager;
import com.porto.app.model.Post;

import java.util.ArrayList;
import java.util.List;

public class SocialViewModel extends ViewModel {
    private Model model;

    public SocialViewModel() {
        model = ModelManager.getInstance();
    }

    public LiveData<List<Post>> getPosts() {
        return model.getAllPosts();
    }

    // TODO: Implement the ViewModel
}