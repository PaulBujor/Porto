package com.porto.app.ui.comment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.porto.app.model.models.Comment;
import com.porto.app.model.models.holder.PostHolder;
import com.porto.app.repository.CommentRepository;
import com.porto.app.repository.PostRepository;

import java.util.List;

public class CommentViewModel extends ViewModel {
    public PostHolder getPost(String postID) {
        return PostRepository.getInstance().getPost(postID);
    }

    public void addComments(Comment comment) {
        CommentRepository.getInstance().addComment(comment);
    }

    public LiveData<List<Comment>> getComments(String postID) {
        return CommentRepository.getInstance().getCommentsOfPost(postID);
    }
    // TODO: Implement the ViewModel
}