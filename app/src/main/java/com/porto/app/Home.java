package com.porto.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.nio.MappedByteBuffer;

public class Home extends Fragment {
    private NavController navController;
    private BottomNavigationView navigationView;

    private Context fragmentContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
        navController = navHostFragment.getNavController();

        //setupt bottom navigation
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.socialFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        //setup app bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        fragmentContext = context;
        super.onAttach(context);
    }
}