package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sanojpunchihewa.glowbutton.GlowButton;

public class ClientPriceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_price);

        GlowButton back_button = (GlowButton) findViewById(R.id.client_price_back_button);
        back_button.setOnClickListener(view -> finish());

        GlowButton next_button = (GlowButton) findViewById(R.id.client_price_confirm_button);
        next_button.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ClientPriceActivity.class);
//            intent.putExtra("client_date", clientDate);
//            startActivity(intent);
        });
    }
}