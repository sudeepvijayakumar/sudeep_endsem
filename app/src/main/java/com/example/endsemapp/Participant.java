package com.example.endsemapp;

import java.io.Serializable;

public class Participant implements Serializable {
    private String name;
    private double contribution;

    public Participant(String name, double contribution) {
        this.name = name;
        this.contribution = contribution;
    }

    public String getName() {
        return name;
    }

    public double getContribution() {
        return contribution;
    }
}