package com.porto.app.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.porto.app.R;
import com.porto.app.model.adapter.PostAdapter;
import com.porto.app.model.models.holder.PostHolder;

import java.util.List;

public class ProfileFragment extends Fragment {
    private TextView name;
    private ImageView profile;
    private RecyclerView postList;

    private Button logOutButton;

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        name = view.findViewById(R.id.username);
        profile = view.findViewById(R.id.profileImage);

        profile.setImageResource(R.drawable.ic_profile);

        logOutButton = view.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(v -> logOut(v));

        postList = view.findViewById(R.id.profilePostRecycler);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));
        //postList.hasFixedSize();

        return view;
    }

    private void logOut(View v) {
        FirebaseAuth.getInstance().signOut();

        View mainNavView = requireActivity().findViewById(R.id.nav_view);
        Navigation.findNavController(mainNavView).navigate(R.id.logOutAction);

//        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.logOutAction);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.getUser().getUsername().observeForever(username -> {
            name.setText(username);
        });

        mViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            PostAdapter adapter = new PostAdapter(posts);
            postList.setAdapter(adapter);
        });
    }
}