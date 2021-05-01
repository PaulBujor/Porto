package com.porto.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(1);
        NavigationUI.setupWithNavController(navigationView, navController);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        fragmentContext = context;
        super.onAttach(context);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
//        navController = navHostFragment.getNavController();
//        navigationView = findViewById(R.id.nav_view);
//        NavigationUI.setupWithNavController(navigationView, navController);
//    }
}