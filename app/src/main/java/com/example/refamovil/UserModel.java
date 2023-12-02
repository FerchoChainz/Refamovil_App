package com.example.refamovil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserModel extends ViewModel {
    private MutableLiveData<String> tuStringMutableLiveData = new MutableLiveData<>();

    public void setUsername(String username) {
        tuStringMutableLiveData.setValue(username);
    }

    public LiveData<String> getUsername() {
        return tuStringMutableLiveData;
    }
}
