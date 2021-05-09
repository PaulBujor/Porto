package com.porto.app.ui.login;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.porto.app.R;

public class LogInFragment extends Fragment {
    private static final int RC_SIGN_IN = 123;

    private Button log_in;
    private Button register;

    private TextInputEditText email;
    private TextInputEditText password;

    private LogInViewModel mViewModel;

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        log_in = view.findViewById(R.id.log_in_button);
        register = view.findViewById(R.id.register_button);

        log_in.setOnClickListener((v) -> logIn(v));
        register.setOnClickListener((v) -> open_register(v));

        email = view.findViewById(R.id.loginEmailEditInput);
        password = view.findViewById(R.id.loginPasswordEditInput);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            logInUser(FirebaseAuth.getInstance().getCurrentUser());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        // TODO: Use the ViewModel
    }

    private void open_register(View v) {
        NavHostFragment.findNavController(this).navigate(R.id.openRegisterAction);
    }

    private void logIn(View v) {
        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            logInUser(user);
                        }
                    });
        } catch (Exception e) {
            Log.i("Log in", "Email or password are empty");
        }
    }

    private void logInUser(FirebaseUser user) {
        try {
            mViewModel.logInUser(user);
        } catch (NullPointerException e) {
            mViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
            mViewModel.logInUser(user);
        }
        NavHostFragment.findNavController(this).navigate(R.id.openHome);
    }

}