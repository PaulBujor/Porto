package com.porto.app.repository;

import androidx.lifecycle.LiveData;

import com.porto.app.dao.CommentDao;
import com.porto.app.model.models.Comment;
import com.porto.app.model.models.holder.PostHolder;

import java.util.List;

public class CommentRepository {
    private CommentDao commentDao;

    private static CommentRepository instance;
    private static Object lock = new Object();

    public CommentRepository() {
        commentDao = CommentDao.getInstance();
    }

    public static CommentRepository getInstance() {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new CommentRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<Comment>> getCommentsOfPost(PostHolder post) {
        return getCommentsOfPost(post.getPostUID());
    }

    public LiveData<List<Comment>> getCommentsOfPost(String postID) {
        return commentDao.getCommentsOfPost(postID);
    }

    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }
}
