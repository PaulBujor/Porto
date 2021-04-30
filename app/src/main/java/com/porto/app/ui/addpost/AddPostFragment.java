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

import com.porto.app.R;
import com.porto.app.model.Post;

public class AddPostFragment extends Fragment {
    private ImageButton backButton;
    private Button addPostButton;
    private EditText textField;

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

        return view;
    }

    private void goBack(View v) {
        NavHostFragment.findNavController(this).navigate(R.id.backHomeFromCreateAction);
    }

    private void addPost(View v) {
        mViewModel.addPost(new Post(textField.getText().toString()));
        NavHostFragment.findNavController(this).navigate(R.id.backHomeFromCreateAction);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddPostViewModel.class);
        // TODO: Use the ViewModel
    }

}