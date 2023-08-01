package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class IncomeCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_category);

        LinearLayout client_layout = findViewById(R.id.new_income_layout);
        client_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewIncomeActivity.class);
            startActivity(intent);
        });

        LinearLayout history_layout = findViewById(R.id.history_layout);
        history_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, JournalActivity.class);
            startActivity(intent);
        });

        LinearLayout statistics_layout = findViewById(R.id.statistics_layout);
        statistics_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
        });
    }
}