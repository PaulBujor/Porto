package com.porto.app.ui.addpost;

import androidx.lifecycle.ViewModel;

import com.porto.app.model.Model;
import com.porto.app.model.models.Post;
import com.porto.app.model.models.User;
import com.porto.app.repository.PostRepository;

public class AddPostViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public AddPostViewModel() {
    }

    public void addPost(Post post) {
        post.setWrittenBy(Model.getInstance().getCurrentUser().getUID());
        post.setTimestamp(System.currentTimeMillis());
        PostRepository.getInstance().addPost(post);
    }

    public User getUser() {
        return Model.getInstance().getCurrentUser();
    }
}