package com.porto.app.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.porto.app.R;
import com.porto.app.manager.Model;

public class RegisterFragment extends Fragment {
    private ImageButton backButton;
    private Button register;

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;

    private RegisterViewModel mViewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        backButton = view.findViewById(R.id.register_back);
        backButton.setOnClickListener(v -> backToLogin(v));

        register = view.findViewById(R.id.register_reg_button);
        register.setOnClickListener(v -> register(v));

        username = view.findViewById(R.id.register_usernameField);
        email = view.findViewById(R.id.register_emailField);
        password = view.findViewById(R.id.register_passwordField);
        passwordConfirm = view.findViewById(R.id.register_passwordConfirmField);

        return view;
    }

    private void register(View v) {
        try {
            mViewModel.register(username.getText().toString(), email.getText().toString(), password.getText().toString(), passwordConfirm.getText().toString());
            backToLogin(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

    private void backToLogin(View v) {
        NavHostFragment.findNavController(this).popBackStack();
    }

}