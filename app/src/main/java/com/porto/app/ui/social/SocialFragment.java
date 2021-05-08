package com.porto.app.ui.social;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.porto.app.R;
import com.porto.app.model.adapter.PostAdapter;
import com.porto.app.model.models.holder.PostHolder;

import java.util.List;

public class SocialFragment extends Fragment {
    private FloatingActionButton createPostFAB;
    private RecyclerView postList;

    private SocialViewModel mViewModel;

    public static SocialFragment newInstance() {
        return new SocialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_fragment, container, false);
        postList = view.findViewById(R.id.postRecycler);
//        postList.hasFixedSize();
        createPostFAB = view.findViewById(R.id.createPostFAB);
        createPostFAB.setOnClickListener(v -> createPost(v));


        return view;
    }

    private void createPost(View v) {
        NavHostFragment.findNavController(this).navigate(R.id.addPostAction);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SocialViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.getPosts().observe(getViewLifecycleOwner(), postsObserver);
    }

    private Observer<List<PostHolder>> postsObserver = new Observer<List<PostHolder>>() {
        @Override
        public void onChanged(List<PostHolder> posts) {
            PostAdapter adapter = new PostAdapter(posts);
            postList.setLayoutManager(new LinearLayoutManager(getContext()));
            postList.setAdapter(adapter);
        }
    };

}