package com.example.coachapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import at.markushi.ui.CircleButton;

public class NewNote extends AppCompatActivity {

    private ArrayList<String> client_names;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        String client_name_extra = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client_name_extra = extras.getString("client_name_extra");
        }

        CircleButton circle_button = findViewById(R.id.circle_button);

        AutoCompleteTextView auto_select_client = findViewById(R.id.auto_select_client);
        auto_select_client.setText(client_name_extra);

        TextView error_select_client = findViewById(R.id.error_select_client);

        EditText notes_edit_text = findViewById(R.id.notes_edit_text);
        notes_edit_text.setOnTouchListener((v, event) -> {
            if (notes_edit_text.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getClientsCursor();
            client_names = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                    client_names.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    client_names);
            auto_select_client.setAdapter(arrayAdapter);
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        circle_button.setOnClickListener(view -> {
            String client_name = auto_select_client.getText().toString();
            String notes = notes_edit_text.getText().toString();
            if (client_names.contains(client_name)) {
                if (notes.length() != 0) {
                    error_select_client.setVisibility(View.INVISIBLE);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
                    String currentDateAndTime = sdf.format(new Date());
                    try {
                        CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
                        coachAppOpenHelper.insertNoteInDB(client_name, currentDateAndTime, notes);
                        Toast.makeText(this, "Новий запис додано в щоденник!", Toast.LENGTH_LONG).show();
                        finish();
                    } catch (SQLiteException e) {
                        Toast toast = Toast.makeText(this, "База даних недоступна",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    error_select_client.setVisibility(View.VISIBLE);
                    error_select_client.setText("*Запис не може бути порожнім");
                }
            } else {
                error_select_client.setVisibility(View.VISIBLE);
                error_select_client.setText("*Даний клієнт не існує");
            }
        });
    }
}