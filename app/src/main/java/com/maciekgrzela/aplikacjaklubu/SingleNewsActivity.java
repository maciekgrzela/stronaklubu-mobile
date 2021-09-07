package com.maciekgrzela.aplikacjaklubu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class SingleNewsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView newsContentTextView;
    private TextView newsAuthorTextView;
    private TextView newsDateTextView;
    private ImageView newsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);

        getSupportActionBar().hide();


        String newsTitle = getIntent().getExtras().getString("news_title");
        String newsContent = getIntent().getExtras().getString("news_content");
        String newsAuthor = getIntent().getExtras().getString("news_author");
        String newsDate = getIntent().getExtras().getString("news_date");
        String newsImagePath = getIntent().getExtras().getString("news_img_path");


        collapsingToolbarLayout = findViewById(R.id.single_news_collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(newsTitle);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);

        newsContentTextView = findViewById(R.id.single_news_description);
        newsAuthorTextView = findViewById(R.id.single_newsAuthor);
        newsDateTextView = findViewById(R.id.single_newsDate);
        newsImageView = findViewById(R.id.single_thumbnail);

        newsContentTextView.setText(newsContent);
        newsAuthorTextView.setText("Autor: "+newsAuthor);
        newsDateTextView.setText("Opublikowano: "+newsDate);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        Glide.with(this).load(newsImagePath).apply(requestOptions).into(newsImageView);
    }
}
