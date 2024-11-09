package com.example.endsemapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button shareButton;
    private Button homeButton;
    private List<Participant> participants;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultTextView = findViewById(R.id.resultTextView);
        shareButton = findViewById(R.id.shareButton);
        homeButton = findViewById(R.id.homeButton);

        // Get data from intent
        participants = (List<Participant>) getIntent().getSerializableExtra("participants");
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0);

        // Calculate and display results
        String result = calculateAndDisplayResults();
        resultTextView.setText(result);

        // Share button functionality
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareResults(result);
            }
        });

        // Home button functionality
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private String calculateAndDisplayResults() {
        // Calculate total contribution and balances
        Map<String, Double> balances = new HashMap<>();
        double equalShare = totalAmount / participants.size();

        for (Participant participant : participants) {
            double balance = participant.getContribution() - equalShare;
            balances.put(participant.getName(), balance);
        }

        // Minimize transactions
        List<String> transactions = minimizeTransactions(balances);

        // Generate result string
        StringBuilder result = new StringBuilder();
        for (String transaction : transactions) {
            result.append(transaction).append("\n");
        }

        return result.toString();
    }

    private List<String> minimizeTransactions(Map<String, Double> balances) {
        List<String> transactions = new ArrayList<>();
        List<Map.Entry<String, Double>> creditors = new ArrayList<>();
        List<Map.Entry<String, Double>> debtors = new ArrayList<>();

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            if (entry.getValue() > 0) {
                creditors.add(entry);
            } else if (entry.getValue() < 0) {
                debtors.add(entry);
            }
        }

        int i = 0, j = 0;
        while (i < creditors.size() && j < debtors.size()) {
            Map.Entry<String, Double> creditor = creditors.get(i);
            Map.Entry<String, Double> debtor = debtors.get(j);

            double amount = Math.min(creditor.getValue(), -debtor.getValue());
            transactions.add(debtor.getKey() + " owes " + creditor.getKey() + " $" + amount);

            creditor.setValue(creditor.getValue() - amount);
            debtor.setValue(debtor.getValue() + amount);

            if (creditor.getValue() == 0) {
                i++;
            }
            if (debtor.getValue() == 0) {
                j++;
            }
        }

        return transactions;
    }

    private void shareResults(String result) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, result);
        startActivity(Intent.createChooser(shareIntent, "Share results via"));
    }
}