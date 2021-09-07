package com.maciekgrzela.aplikacjaklubu.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.maciekgrzela.aplikacjaklubu.model.News;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<News>> mText;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<News> newsList;
    private RecyclerView recyclerView;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        newsList = new ArrayList<News>();
        mText.setValue(newsList);
    }

    public LiveData<List<News>> getText() {
        return mText;
    }
}