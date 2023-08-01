package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sanojpunchihewa.glowbutton.GlowButton;

public class NewClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        CoachAppOpenHelper db = new CoachAppOpenHelper(this);

        GlowButton back_button = findViewById(R.id.client_name_back_button);

        GlowButton confirm_button = findViewById(R.id.client_name_confirm_button);

        EditText client_name_edit = findViewById(R.id.client_name_edit);
        client_name_edit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String client_name = client_name_edit.getText().toString();
                confirm_button.setEnabled(client_name.length() != 0);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        back_button.setOnClickListener(view -> finish());
        confirm_button.setOnClickListener(view -> {
            db.addClient(client_name_edit.getText().toString());
            finish();
        });
    }
}