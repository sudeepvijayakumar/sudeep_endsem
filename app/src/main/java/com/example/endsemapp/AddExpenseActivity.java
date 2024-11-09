package com.example.endsemapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText titleEditText, amountEditText;
    private LinearLayout participantsLayout;
    private Button addParticipantButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        titleEditText = findViewById(R.id.titleEditText);
        amountEditText = findViewById(R.id.amountEditText);
        participantsLayout = findViewById(R.id.participantsLayout);
        addParticipantButton = findViewById(R.id.addParticipantButton);
        saveButton = findViewById(R.id.saveButton);

        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addParticipantField();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save the expense details
                String title = titleEditText.getText().toString();
                double amount = Double.parseDouble(amountEditText.getText().toString());

                // Collect participant details and contributions
                int participantCount = participantsLayout.getChildCount();
                List<Participant> participants = new ArrayList<>();

                for (int i = 0; i < participantCount; i++) {
                    View participantView = participantsLayout.getChildAt(i);
                    EditText nameEditText = participantView.findViewById(R.id.participantNameEditText);
                    EditText contributionEditText = participantView.findViewById(R.id.participantContributionEditText);

                    String name = nameEditText.getText().toString();
                    double contribution = Double.parseDouble(contributionEditText.getText().toString());

                    participants.add(new Participant(name, contribution));
                }

                // Pass data to ResultActivity
                Intent intent = new Intent(AddExpenseActivity.this, ResultActivity.class);
                intent.putExtra("participants", (ArrayList<Participant>) participants);
                intent.putExtra("totalAmount", amount);
                startActivity(intent);
            }
        });
    }

    private void addParticipantField() {
        View participantView = getLayoutInflater().inflate(R.layout.item_participant, null);
        participantsLayout.addView(participantView);
    }
}