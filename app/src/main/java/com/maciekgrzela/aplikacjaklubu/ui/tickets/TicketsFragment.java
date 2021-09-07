package com.maciekgrzela.aplikacjaklubu.ui.tickets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.maciekgrzela.aplikacjaklubu.R;
import com.maciekgrzela.aplikacjaklubu.adapter.RecyclerViewAdapter;
import com.maciekgrzela.aplikacjaklubu.adapter.TicketsViewAdapter;
import com.maciekgrzela.aplikacjaklubu.model.News;
import com.maciekgrzela.aplikacjaklubu.model.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment {

    private TicketsViewModel ticketsViewModel;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private int currentUserIdentifier;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ticketsViewModel =
                ViewModelProviders.of(this).get(TicketsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_tickets, container, false);

        recyclerView = root.findViewById(R.id.ticketsContainer);
        currentUserIdentifier = getActivity().getIntent().getExtras().getInt("userid");
        jsonRequest(root.getContext());
        
        ticketsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(@Nullable List<Ticket> tickets) {
                setUpRecyclerView(tickets, root.getContext());
            }
        });
        return root;
    }

    private void jsonRequest(final Context context) {
        final List<Ticket> ticketList = new ArrayList<Ticket>();
        request = new JsonArrayRequest(getString(R.string.api_url)+"/tickets/read_specific.php?client_ID="+currentUserIdentifier, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                for(int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Ticket ticket = new Ticket();
                        ticket.setClubHome(jsonObject.getString("clubHome"));
                        ticket.setClubAway(jsonObject.getString("clubAway"));
                        ticket.setPrice(jsonObject.getDouble("price"));
                        ticket.setSeat(jsonObject.getString("seat"));
                        ticket.setDate(LocalDateTime.parse(jsonObject.getString("match_date"), formatter));
                        ticketList.add(ticket);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                setUpRecyclerView(ticketList, context);
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

    private void setUpRecyclerView(List<Ticket> ticketList, Context context) {
        TicketsViewAdapter ticketsViewAdapter = new TicketsViewAdapter(context, ticketList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(ticketsViewAdapter);
    }
}
