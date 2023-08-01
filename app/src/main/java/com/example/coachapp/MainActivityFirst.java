package com.example.coachapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import at.markushi.ui.CircleButton;

public class MainActivityFirst extends AppCompatActivity {

    private float training_price = 100;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleButton button = (CircleButton) findViewById(R.id.circle_button);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ClientNameActivity.class);
            startActivity(intent);
        });

        EditText editText = (EditText) findViewById(R.id.training_price_edit);
//        editText.setOnFocusChangeListener((view, hasFocus) -> {
//            if (!hasFocus && editText.getText().toString().length() == 0) {
//                editText.setText(Float.toString(training_price));
//            }
//        });
//        editText.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                if (editText.getText().toString().length() == 0) {
//                    editText.setText(Float.toString(training_price));
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.training_price_frame);
        frameLayout.setOnClickListener(view -> {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        });
    }
}