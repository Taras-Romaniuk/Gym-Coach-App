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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sanojpunchihewa.glowbutton.GlowButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewIncomeActivity extends AppCompatActivity {

    private static ArrayList<String> client_names;
    private static final String STATE_DEFAULT = "unsigned";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);

        String client_name_extra = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client_name_extra = extras.getString("client_name_extra");
        }

        TextView error_select_client = findViewById(R.id.error_select_client);

        GlowButton back_button = findViewById(R.id.new_income_back_button);
        back_button.setOnClickListener(view -> finish());

        GlowButton confirm_button = findViewById(R.id.new_income_confirm_button);

        EditText client_income_edit = findViewById(R.id.client_income);
        client_income_edit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                confirm_button.setEnabled(client_income_edit.getText().toString().length() != 0);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        TimePicker timePicker = findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        DatePicker datePicker = findViewById(R.id.date_picker);

        CheckBox check_box_date = findViewById(R.id.checkbox_date);
        check_box_date.setOnClickListener(view -> {
            if (check_box_date.isChecked()) {
                timePicker.setVisibility(View.GONE);
                datePicker.setVisibility(View.GONE);
            } else {
                timePicker.setVisibility(View.VISIBLE);
                datePicker.setVisibility(View.VISIBLE);
            }
        });

        AutoCompleteTextView auto_select_client = findViewById(R.id.auto_select_client);
        auto_select_client.setText(client_name_extra);

        confirm_button.setOnClickListener(view -> {
            String client_name = auto_select_client.getText().toString();
            if (client_names.contains(client_name)) {
                String currentDateandTime;
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
                if (check_box_date.isChecked()) {
                    error_select_client.setVisibility(View.GONE);
                    currentDateandTime = sdf.format(new Date());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
                    currentDateandTime = sdf.format(calendar.getTime());
                }
                try {
                    CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
                    coachAppOpenHelper.insertIncomeInDB(client_name, currentDateandTime, STATE_DEFAULT, Float.parseFloat(client_income_edit.getText().toString()));
                    Toast.makeText(this, "Нову оплату успішно створено!", Toast.LENGTH_LONG).show();
                } catch (SQLiteException e) {
                    Toast toast = Toast.makeText(this, "База даних недоступна",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                error_select_client.setVisibility(View.VISIBLE);
                error_select_client.setText("*Даний клієнт не існує");
            }
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
    }
}