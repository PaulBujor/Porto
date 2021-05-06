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
import com.porto.app.R;

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
            if(passwordConfirm.getText().toString().equals(password.getText().toString())) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(logInTask -> {
                            if (logInTask.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                mViewModel.registerUser(user, username.getText().toString());
                            }
                            FirebaseAuth.getInstance().signOut();
                        });
                        backToLogin(v);
                    } else {
                        Log.i("Register", task.getException().getMessage());
                    }
                });
            } else
                Log.e("Register", "Passwords do not match");
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