package com.porto.app.ui.profile;

import androidx.lifecycle.ViewModel;

import com.porto.app.manager.Model;
import com.porto.app.model.User;

public class ProfileViewModel extends ViewModel {
    public User getUser() {
        return Model.getInstance().getCurrentUser();
    }
}