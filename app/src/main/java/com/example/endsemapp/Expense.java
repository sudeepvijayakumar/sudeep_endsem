package com.example.endsemapp;

import java.io.Serializable;
import java.util.List;

public class Expense implements Serializable {
    private String title;
    private double amount;
    private List<Participant> participants;

    public Expense(String title, double amount, List<Participant> participants) {
        this.title = title;
        this.amount = amount;
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
}