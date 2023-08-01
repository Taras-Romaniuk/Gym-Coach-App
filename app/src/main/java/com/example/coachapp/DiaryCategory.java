package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class DiaryCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_category);

        LinearLayout view_diary_layout = findViewById(R.id.view_diary_layout);
        view_diary_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, ViewDiaryActivity.class);
            startActivity(intent);
        });

        LinearLayout new_note_layout = findViewById(R.id.new_note_layout);
        new_note_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewNote.class);
            startActivity(intent);
        });
    }
}