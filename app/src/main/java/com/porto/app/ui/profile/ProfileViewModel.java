package com.porto.app.ui.profile;

import androidx.lifecycle.ViewModel;

import com.porto.app.manager.Model;
import com.porto.app.model.User;

public class ProfileViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private User user;

    public ProfileViewModel() {
        user = Model.getInstance().getCurrentUser();
    }

    public User getUser() {
        return user;
    }
}