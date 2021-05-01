package com.porto.app.ui.addpost;

import androidx.lifecycle.ViewModel;

import com.porto.app.manager.Model;
import com.porto.app.model.Post;
import com.porto.app.model.User;
import com.porto.app.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import javax.sql.RowSet;

public class AddPostViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public AddPostViewModel() {
    }

    public void addPost(Post post) {
        post.setWrittenBy(Model.getInstance().getCurrentUser());
        post.setTimestamp(System.currentTimeMillis());
        PostRepository.getInstance().addPost(post);
    }

    public User getUser() {
        return Model.getInstance().getCurrentUser();
    }
}