package com.maciekgrzela.aplikacjaklubu.ui.navi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HowToGetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HowToGetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
