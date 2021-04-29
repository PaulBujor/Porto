package com.porto.app.manager;

import androidx.lifecycle.MutableLiveData;

import com.porto.app.model.Post;
import com.porto.app.model.User;

import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Model {
    //Singleton
    private static ModelManager instance;
    private static Object threadLock = new Object();

    public static ModelManager getInstance() {
        if(instance == null) {
            synchronized (threadLock) {
                if(instance == null) {
                    instance = new ModelManager();
                }
            }
        }
        return instance;
    }

    private User currentUser;
    private MutableLiveData<List<Post>> posts;

    private ModelManager() {
        posts = new MutableLiveData<>();
        ArrayList<Post> tempPost = new ArrayList<>();
        currentUser = new User(0, "johndoe", "John Doe");
        tempPost.add(new Post(currentUser, "1. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 50));
        tempPost.add(new Post(currentUser, "2. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 60));
        tempPost.add(new Post(currentUser, "3. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 40));
        tempPost.add(new Post(currentUser, "4. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 70));
        tempPost.add(new Post(currentUser, "5. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 20));
        tempPost.add(new Post(currentUser, "6. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 90));
        tempPost.add(new Post(currentUser, "7. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 50));
        tempPost.add(new Post(currentUser, "8. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 60));
        tempPost.add(new Post(currentUser, "9. Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 30));
        posts.setValue(tempPost);
    }

    public MutableLiveData<List<Post>> getAllPosts() {
        return posts;
    }
}
