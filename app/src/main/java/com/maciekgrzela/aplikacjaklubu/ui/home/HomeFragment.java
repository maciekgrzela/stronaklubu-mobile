package com.maciekgrzela.aplikacjaklubu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.maciekgrzela.aplikacjaklubu.NewsDrawerActivity;
import com.maciekgrzela.aplikacjaklubu.R;
import com.maciekgrzela.aplikacjaklubu.adapter.RecyclerViewAdapter;
import com.maciekgrzela.aplikacjaklubu.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.newsContainer);
        Log.d("W HOME FRAGMENCIE: ",getActivity().getIntent().getExtras().getString("userFirstName"));
        jsonRequest(root.getContext());
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                setUpRecyclerView(news, root.getContext());
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void jsonRequest(final Context context) {
        final List<News> newsList = new ArrayList<News>();
        request = new JsonArrayRequest(getString(R.string.api_url)+"/news/read.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        News news = new News();
                        news.setTitle(jsonObject.getString("title"));
                        news.setWorkerFirstName(jsonObject.getString("worker_first_name"));
                        news.setContentPath(jsonObject.getString("content_path"));
                        news.setWorkerLastName(jsonObject.getString("worker_last_name"));
                        news.setImagePath(getString(R.string.assets_img_url)+"/articles/"+jsonObject.getString("news_img_path")+".jpg");
                        news.setCreatedAt(LocalDateTime.parse(jsonObject.getString("created_at"), formatter));
                        newsList.add(news);

                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                setUpRecyclerView(newsList, context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("RESPONSE ERROR", error.toString());
            }
        });

        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    private void setUpRecyclerView(List<News> newsList, Context context) {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(context, newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
