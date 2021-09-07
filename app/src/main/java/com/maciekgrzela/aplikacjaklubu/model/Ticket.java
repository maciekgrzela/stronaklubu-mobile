package com.maciekgrzela.aplikacjaklubu.model;

import java.time.LocalDateTime;

public class Ticket {

    private String clubHome;
    private String clubAway;
    private double price;
    private String seat;
    private LocalDateTime date;

    public Ticket(String clubHome, String clubAway, double price, String seat, LocalDateTime date) {
        this.clubHome = clubHome;
        this.clubAway = clubAway;
        this.price = price;
        this.seat = seat;
        this.date = date;
    }

    public Ticket() {
    }

    public String getClubHome() {
        return clubHome;
    }

    public void setClubHome(String clubHome) {
        this.clubHome = clubHome;
    }

    public String getClubAway() {
        return clubAway;
    }

    public void setClubAway(String clubAway) {
        this.clubAway = clubAway;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
