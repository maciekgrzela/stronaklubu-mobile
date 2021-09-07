package com.maciekgrzela.aplikacjaklubu.ui.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maciekgrzela.aplikacjaklubu.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketsViewModel extends ViewModel {

    private MutableLiveData<List<Ticket>> mText;
    private List<Ticket> ticketList;

    public TicketsViewModel() {
        mText = new MutableLiveData<>();
        ticketList = new ArrayList<Ticket>();
        mText.setValue(ticketList);
    }

    public LiveData<List<Ticket>> getText() {
        return mText;
    }
}

