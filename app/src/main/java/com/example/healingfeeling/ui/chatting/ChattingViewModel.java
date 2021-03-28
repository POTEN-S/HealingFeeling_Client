package com.example.healingfeeling.ui.chatting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class ChattingViewModel extends ViewModel {

    public HashMap<String,String> users;
    private MutableLiveData<String> mText;

    public ChattingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is chatting fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}