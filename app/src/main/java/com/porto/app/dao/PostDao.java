package com.porto.app.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.porto.app.model.models.Post;
import com.porto.app.model.models.User;
import com.porto.app.model.models.holder.PostHolder;
import com.porto.app.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PostDao {
    private MutableLiveData<List<PostHolder>> posts;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    private static PostDao instance;
    private static Object lock = new Object();

    private PostDao() {
        ref = database.getReference("porto-social-app-default-rtdb");

        posts = new MutableLiveData<>();
        ref.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PostHolder> currentPosts = new ArrayList<>();
                try {
                    DataSnapshot subSnapshot;
                    Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();

                    //iterate through all elements from posts tree
                    while (null != (subSnapshot = iterator.next())) {
                        PostHolder postHolder = new PostHolder(subSnapshot.getKey());

                        //keep reference to post as it is updated with data
                        Post post = new Post();
                        postHolder.setPost(post);

                        for (DataSnapshot child : subSnapshot.getChildren())
                            switch (child.getKey()) {
                                case "text":
                                    post.setText(child.getValue().toString());
                                    break;
                                case "timestamp":
                                    post.setTimestamp(child.getValue(Long.class));
                                    break;
                                case "writtenBy":
                                    User user = new User();
                                    user.setUID(String.valueOf(child.getValue()));
                                    user.setUsername(UserRepository.getInstance().getUsername(user.getUID()));
                                    post.setWrittenBy(user.getUID());
                                    postHolder.setWrittenBy(user);
                                    break;
                            }
                        currentPosts.add(postHolder);
                    }
                } catch (Exception e) {
                    Log.e("Firebase deserialization", "This might be because there are no objects in the database");
                    Log.e("Firebase error", e.getStackTrace().toString());
                }
                Collections.sort(currentPosts, Collections.reverseOrder());
                posts.setValue(currentPosts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    public static PostDao getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PostDao();
                }
            }
        }
        return instance;
    }

    public LiveData<List<PostHolder>> getAllPosts() {
        return posts;
    }

    public void addPost(Post post) {
        DatabaseReference postsRef = ref.child("posts");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(post);

//        List<Post> currentPosts = posts.getValue();
//        currentPosts.add(post);
//        posts.setValue(currentPosts);
    }

    public PostHolder getPost(String postID) {
        for (PostHolder holder : posts.getValue())
            if (holder.getPostUID().equals(postID))
                return holder;
        return null;
    }
}
