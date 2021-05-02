package com.porto.app.ui.addpost;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.porto.app.R;
import com.porto.app.model.Post;

public class AddPostFragment extends Fragment {
    private ImageView profile;
    private ImageButton backButton;
    private Button addPostButton;
    private EditText textField;
    private TextView profileName;

    private AddPostViewModel mViewModel;

    public static AddPostFragment newInstance() {
        return new AddPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_post_fragment, container, false);
        backButton = view.findViewById(R.id.createPost_back);
        backButton.setOnClickListener(v -> goBack(v));

        addPostButton = view.findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> addPost(v));

        textField = view.findViewById(R.id.toPostText);

        profile = view.findViewById(R.id.profileImage);
        profile.setImageResource(R.drawable.ic_profile);

        profileName = view.findViewById(R.id.username);

        return view;
    }

    private void goBack(View v) {
        NavHostFragment.findNavController(this).popBackStack();
    }

    private void addPost(View v) {
        mViewModel.addPost(new Post(textField.getText().toString()));
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddPostViewModel.class);
        profileName.setText(mViewModel.getUser().getUsername());
        // TODO: Use the ViewModel
    }

}