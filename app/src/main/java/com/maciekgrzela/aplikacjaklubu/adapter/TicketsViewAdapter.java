package com.maciekgrzela.aplikacjaklubu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.maciekgrzela.aplikacjaklubu.R;
import com.maciekgrzela.aplikacjaklubu.SingleNewsActivity;
import com.maciekgrzela.aplikacjaklubu.TicketCodeActivity;
import com.maciekgrzela.aplikacjaklubu.model.Ticket;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TicketsViewAdapter extends RecyclerView.Adapter<TicketsViewAdapter.CustomViewHolder> {

    private Context mContext;
    private List<Ticket> mData;
    private RequestOptions options;

    public TicketsViewAdapter(Context mContext, List<Ticket> mData) {
        this.mContext = mContext;
        this.mData = mData;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.tickets_row_item, parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);

        viewHolder.tickets_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TicketCodeActivity.class);
                intent.putExtra("ticket_club_home", mData.get(viewHolder.getAdapterPosition()).getClubHome());
                intent.putExtra("ticket_club_away", mData.get(viewHolder.getAdapterPosition()).getClubAway());
                intent.putExtra("ticket_seat", mData.get(viewHolder.getAdapterPosition()).getSeat());

                mContext.startActivity(intent);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        holder.tv_clubHome.setText(mData.get(position).getClubHome());
        holder.tv_clubAway.setText(mData.get(position).getClubAway());
        holder.tv_seat.setText("Numer miejsca: "+mData.get(position).getSeat());
        holder.tv_price.setText("Cena biletu: "+mData.get(position).getPrice()+" PLN");
        holder.tv_date.setText("Data meczu: "+mData.get(position).getDate().format(formatter));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tv_clubHome;
        TextView tv_clubAway;
        TextView tv_price;
        TextView tv_seat;
        TextView tv_date;
        ImageView img_thumbnail;
        LinearLayout tickets_container;


        public CustomViewHolder(View itemView){
            super(itemView);
            tickets_container = itemView.findViewById(R.id.tickets_container);
            tv_clubHome = itemView.findViewById(R.id.ticketClubHome);
            tv_clubAway = itemView.findViewById(R.id.ticketClubAway);
            tv_price = itemView.findViewById(R.id.ticketPrice);
            tv_seat = itemView.findViewById(R.id.ticketSeat);
            tv_date = itemView.findViewById(R.id.ticketDate);
            img_thumbnail = itemView.findViewById(R.id.ticket_thumbnail);
        }
    }
}
