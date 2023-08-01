package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import com.sanojpunchihewa.glowbutton.GlowButton;

public class ClientTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_time);

        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        GlowButton back_button = (GlowButton) findViewById(R.id.client_time_back_button);
        back_button.setOnClickListener(view -> finish());

        GlowButton next_button = (GlowButton) findViewById(R.id.client_time_next_button);
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
            Intent intent = new Intent(this, ClientPriceActivity.class);
//            intent.putExtra("client_date", clientDate);
            startActivity(intent);
        });
    }
}