package com.maciekgrzela.aplikacjaklubu.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maciekgrzela.aplikacjaklubu.R;
import com.maciekgrzela.aplikacjaklubu.SingleNewsActivity;
import com.maciekgrzela.aplikacjaklubu.model.News;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private Context mContext;
    private List<News> mData;
    private RequestOptions options;

    public RecyclerViewAdapter(Context mContext, List<News> mData) {
        this.mContext = mContext;
        this.mData = mData;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.news_row_item, parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);

        viewHolder.news_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                Intent intent = new Intent(mContext, SingleNewsActivity.class);
                intent.putExtra("news_title", mData.get(viewHolder.getAdapterPosition()).getTitle());
                intent.putExtra("news_content", mData.get(viewHolder.getAdapterPosition()).getContentPath());
                intent.putExtra("news_author", mData.get(viewHolder.getAdapterPosition()).getWorkerFirstName()+" "+mData.get(viewHolder.getAdapterPosition()).getWorkerLastName());
                intent.putExtra("news_date", mData.get(viewHolder.getAdapterPosition()).getCreatedAt().format(formatter));
                intent.putExtra("news_img_path", mData.get(viewHolder.getAdapterPosition()).getImagePath());

                mContext.startActivity(intent);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_author.setText("Przez: "+mData.get(position).getWorkerFirstName() +" "+ mData.get(position).getWorkerLastName());
        holder.tv_date.setText("Utworzono: "+mData.get(position).getCreatedAt().toString());

        Glide.with(mContext).load(mData.get(position).getImagePath()).apply(options).into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_author;
        TextView tv_date;
        ImageView img_thumbnail;
        LinearLayout news_container;


        public CustomViewHolder(View itemView){
            super(itemView);
            news_container = itemView.findViewById(R.id.news_container);
            tv_title = itemView.findViewById(R.id.newsTitle);
            tv_author = itemView.findViewById(R.id.newsAuthor);
            tv_date = itemView.findViewById(R.id.newsDate);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}
