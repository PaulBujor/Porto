package com.porto.app.ui.social;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.porto.app.model.models.holder.PostHolder;
import com.porto.app.repository.PostRepository;

import java.util.List;

public class SocialViewModel extends ViewModel {

    public SocialViewModel() {

    }

    public LiveData<List<PostHolder>> getPosts() {
        return PostRepository.getInstance().getAllPosts();
    }

    // TODO: Implement the ViewModel
}