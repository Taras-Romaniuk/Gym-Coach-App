package com.example.coachapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView today_client_count_view = findViewById(R.id.today_client_count_view);
        TextView later_client_count_view = findViewById(R.id.later_client_count_view);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String current_date = sdf.format(new Date());

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            int today_client_count = coachAppOpenHelper.getClientsTodayCount(current_date);
            today_client_count_view.setText("" + today_client_count);
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getIncomesCursor();
            int later_client_count = 0;
            if (cursor.moveToFirst()) {
                do {
                    String date = cursor.getString(2).split(" at ")[0];
                    if (isAfterDate(date, current_date)) later_client_count++;
                } while (cursor.moveToNext());
            }
            later_client_count_view.setText("" + later_client_count);
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        LinearLayout today_later_layout = findViewById(R.id.today_later_layout);
        today_later_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);
        });

        LinearLayout schedule_layout = findViewById(R.id.schedule_layout);
        schedule_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, ScheduleActivity.class);
            startActivity(intent);
        });

        LinearLayout diary_layout = findViewById(R.id.diary_layout);
        diary_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, DiaryCategory.class);
            startActivity(intent);
        });
        LinearLayout client_layout = findViewById(R.id.client_layout);
        client_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, ClientCategoryActivity.class);
            startActivity(intent);
        });
        LinearLayout money_layout = findViewById(R.id.money_layout);
        money_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, IncomeCategoryActivity.class);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isAfterDate(String d1, String d2) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            return date1.after(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}