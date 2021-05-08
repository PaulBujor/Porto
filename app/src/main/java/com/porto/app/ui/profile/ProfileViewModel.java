package com.porto.app.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.porto.app.model.Model;
import com.porto.app.model.models.User;
import com.porto.app.model.models.holder.PostHolder;
import com.porto.app.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<List<PostHolder>> posts = new MutableLiveData<>(new ArrayList<>());

    public ProfileViewModel() {
        PostRepository.getInstance().getAllPosts().observeForever(obsPosts -> {
            List<PostHolder> thesePosts = new ArrayList<>(obsPosts);
            thesePosts.removeIf(p -> !p.getWrittenBy().equals(getUser()));
            posts.setValue(thesePosts);
        });
    }

    public User getUser() {
        return Model.getInstance().getCurrentUser();
    }

    public LiveData<List<PostHolder>> getPosts() {
        return posts;
    }

}