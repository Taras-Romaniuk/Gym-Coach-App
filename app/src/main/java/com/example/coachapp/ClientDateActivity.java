package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.sanojpunchihewa.glowbutton.GlowButton;

public class ClientDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_date);

        GlowButton back_button = (GlowButton) findViewById(R.id.client_date_back_button);
        back_button.setOnClickListener(view -> finish());

        GlowButton next_button = (GlowButton) findViewById(R.id.client_date_next_button);
        next_button.setOnClickListener(view -> {
//            DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
//            int year = datePicker.getYear();
//            int month = datePicker.getMonth();
//            int day = datePicker.getDayOfMonth();
//            @SuppressLint("DefaultLocale") String clientDate = String.format("%d.%d.%d", day, month, year);
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(year, month, day);
//
//            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
//            String strDate = format.format(calendar.getTime());
            Intent intent = new Intent(this, ClientTimeActivity.class);
//            intent.putExtra("client_date", clientDate);
            startActivity(intent);
        });
    }
}