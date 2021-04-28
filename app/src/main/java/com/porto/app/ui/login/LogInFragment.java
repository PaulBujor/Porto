package com.porto.app.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.porto.app.R;

public class LogInFragment extends Fragment {

    private Button log_in;
    private Button register;

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
//        navController.navigate(R.id.openRegisterAction);
    }

    private void logIn(View v) {
        NavHostFragment.findNavController(this).navigate(R.id.openHome);
    }

}