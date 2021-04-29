package com.porto.app.ui.social;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.porto.app.R;
import com.porto.app.manager.Model;
import com.porto.app.manager.ModelManager;
import com.porto.app.model.Post;
import com.porto.app.model.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {
    private RecyclerView postList;
    private Model model;

    private SocialViewModel mViewModel;

    public static SocialFragment newInstance() {
        return new SocialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_fragment, container, false);
        postList = view.findViewById(R.id.postRecycler);
        postList.hasFixedSize();
        //todo
        //model = new ModelManager();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SocialViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getPosts().observe(getViewLifecycleOwner(), postsObserver);
    }

    Observer<List<Post>> postsObserver = new Observer<List<Post>>() {
        @Override
        public void onChanged(List<Post> posts) {
            PostAdapter adapter = new PostAdapter(posts);
            postList.setLayoutManager(new LinearLayoutManager(getContext()));
            postList.setAdapter(adapter);
        }
    };

}