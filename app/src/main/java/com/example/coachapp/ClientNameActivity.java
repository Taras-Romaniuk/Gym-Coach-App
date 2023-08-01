package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sanojpunchihewa.glowbutton.GlowButton;

public class ClientNameActivity extends AppCompatActivity {

    private String client_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_name);

        GlowButton back_button = (GlowButton) findViewById(R.id.client_name_back_button);
        back_button.setOnClickListener(view -> finish());

        GlowButton next_button = (GlowButton) findViewById(R.id.client_name_next_button);
        next_button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ClientDateActivity.class);
//            intent.putExtra("client_name", client_name);
            startActivity(intent);
        });

        EditText client_name_edit = (EditText) findViewById(R.id.client_name);
        client_name_edit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                client_name = client_name_edit.getText().toString();
                next_button.setEnabled(client_name.length() != 0);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
}