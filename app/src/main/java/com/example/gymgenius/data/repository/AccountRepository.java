package com.example.gymgenius.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AccountRepository {

    private MutableLiveData<String> userData = new MutableLiveData<>();

    // Constructor, otros métodos y lógica...

    public LiveData<String> getUserData() {
        return userData;
    }

    public void updateUserData(String newText) {
        userData.setValue(newText);
    }
}