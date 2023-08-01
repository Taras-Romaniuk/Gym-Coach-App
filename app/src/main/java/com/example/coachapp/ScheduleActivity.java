package com.example.coachapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private static final String STATE_UNSIGNED = "unsigned";
    private static final String STATE_SIGNED = "signed";
    private static final String STATE_ABSENT = "absent";


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        LinearLayout today_layout = findViewById(R.id.today_layout);
        LinearLayout later_day_layout = findViewById(R.id.later_day_layout);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String current_date = sdf.format(new Date());

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getClientsTodayCursor(current_date);
            if (cursor.moveToFirst()) {
                do {
                    int client_income_id = cursor.getInt(0);
                    String client_name = cursor.getString(1);
                    String client_time = cursor.getString(2).split(" at ")[1];
                    String client_income_state = cursor.getString(3);
                    float client_income = cursor.getFloat(4);

                    LinearLayout horizontal_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    horizontal_layout.setLayoutParams(horizontal_layout_params);
                    int size = getPixelValueOfDP(this, 4);
                    horizontal_layout.setPadding(size, size, size, size);
                    horizontal_layout.setBackgroundResource(R.drawable.underline);

                    LinearLayout vertical_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams vertical_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    vertical_layout_params.gravity = Gravity.CENTER_VERTICAL;
                    vertical_layout.setLayoutParams(vertical_layout_params);
                    vertical_layout.setOrientation(LinearLayout.VERTICAL);

                    size = getPixelValueOfDP(this, 100);
                    TextView client_name_view = new TextView(this);
                    LinearLayout.LayoutParams client_name_view_params = new LinearLayout.LayoutParams(size, LinearLayout.LayoutParams.WRAP_CONTENT);
                    client_name_view.setLayoutParams(client_name_view_params);
                    client_name_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                    client_name_view.setTextColor(getResources().getColor(R.color.name_color));
                    client_name_view.setText(client_name);

                    TextView client_income_view = new TextView(this);
                    LinearLayout.LayoutParams client_income_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    client_income_view.setLayoutParams(client_income_view_params);
                    client_income_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    client_income_view.setText("" + client_income);

                    LinearLayout horizontal_layout2 = new LinearLayout(this);
                    LinearLayout.LayoutParams horizontal_layout2_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    horizontal_layout2_params.gravity = Gravity.CENTER_VERTICAL;
                    size = getPixelValueOfDP(this, 6);
                    horizontal_layout2_params.setMargins(size, size, size, size);
                    horizontal_layout2.setLayoutParams(horizontal_layout2_params);

                    TextView client_time_view = new TextView(this);
                    LinearLayout.LayoutParams client_time_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    client_time_view_params.gravity = Gravity.CENTER_VERTICAL;
                    client_time_view.setLayoutParams(client_time_view_params);
                    client_time_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                    client_time_view.setText(client_time);

                    int client_income_view_color = 0; // !!!
                    switch (client_income_state) {
                        case STATE_UNSIGNED:
                            client_income_view_color = getResources().getColor(R.color.journal_income_unsigned_color);
                            break;
                        case STATE_SIGNED:
                            client_income_view_color = getResources().getColor(R.color.journal_income_signed_color);
                            break;
                        case STATE_ABSENT:
                            client_income_view_color = getResources().getColor(R.color.journal_income_absent_color);
                            break;
                    }

                    client_income_view.setTextColor(client_income_view_color);
                    client_time_view.setTextColor(client_income_view_color);

                    View space = new View(this);
                    LinearLayout.LayoutParams space_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    space.setLayoutParams(space_params);

                    ImageButton check_mark_button = new ImageButton(this);
                    size = getPixelValueOfDP(this, 60);
                    LinearLayout.LayoutParams check_mark_button_params = new LinearLayout.LayoutParams(size, size);
                    size = getPixelValueOfDP(this, 10);
                    check_mark_button_params.setMargins(0, 0, size, 0);
                    check_mark_button.setLayoutParams(check_mark_button_params);
                    check_mark_button.setBackgroundResource(R.drawable.check_mark);

                    ImageButton cancel_button = new ImageButton(this);
                    size = getPixelValueOfDP(this, 60);
                    LinearLayout.LayoutParams cancel_button_params = new LinearLayout.LayoutParams(size, size);
                    cancel_button.setLayoutParams(cancel_button_params);
                    cancel_button.setBackgroundResource(R.drawable.cancel);

                    check_mark_button.setOnClickListener(view -> {
                        coachAppOpenHelper.updateIncomeStateById(STATE_SIGNED, client_income_id);
                        client_time_view.setTextColor(getResources().getColor(R.color.journal_income_signed_color));
                        client_income_view.setTextColor(getResources().getColor(R.color.journal_income_signed_color));
                        check_mark_button.setVisibility(View.INVISIBLE);
                        cancel_button.setVisibility(View.INVISIBLE);
                    });
                    cancel_button.setOnClickListener(view -> {
                        coachAppOpenHelper.updateIncomeStateById(STATE_ABSENT, client_income_id);
                        client_time_view.setTextColor(getResources().getColor(R.color.journal_income_absent_color));
                        client_income_view.setTextColor(getResources().getColor(R.color.journal_income_absent_color));
                        check_mark_button.setVisibility(View.INVISIBLE);
                        cancel_button.setVisibility(View.INVISIBLE);
                    });

                    vertical_layout.addView(client_name_view);
                    vertical_layout.addView(client_income_view);
                    horizontal_layout2.addView(client_time_view);
                    horizontal_layout2.addView(space);
                    if (client_income_state.equals(STATE_UNSIGNED)) {
                        horizontal_layout2.addView(check_mark_button);
                        horizontal_layout2.addView(cancel_button);
                    }
                    horizontal_layout.addView(vertical_layout);
                    horizontal_layout.addView(horizontal_layout2);
                    today_layout.addView(horizontal_layout);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getIncomesASCCursor();
            List<String> unique_dates = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    int client_income_id = cursor.getInt(0);
                    String client_name = cursor.getString(1);
                    String date_time = cursor.getString(2);
                    String client_income_state = cursor.getString(3);
                    float client_income = cursor.getFloat(4);

                    String client_date = date_time.split(" at ")[0];
                    String client_time = date_time.split(" at ")[1];

                    if (isAfterDate(client_date, current_date)) {
                        if (!unique_dates.contains(client_date)) {
                            unique_dates.add(client_date);

                            TextView day_later_view = new TextView(this);
                            LinearLayout.LayoutParams day_later_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            day_later_view_params.gravity = Gravity.CENTER_HORIZONTAL;
                            day_later_view.setLayoutParams(day_later_view_params);
                            day_later_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                            day_later_view.setText(parseDateToUa(client_date));
                            day_later_view.setTextColor(getResources().getColor(R.color.text_color_1));
                            later_day_layout.addView(day_later_view);
                        }

                        LinearLayout horizontal_layout = new LinearLayout(this);
                        LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        horizontal_layout.setLayoutParams(horizontal_layout_params);
                        int size = getPixelValueOfDP(this, 4);
                        horizontal_layout.setPadding(size, size, size, size);
                        horizontal_layout.setBackgroundResource(R.drawable.underline);

                        LinearLayout vertical_layout = new LinearLayout(this);
                        LinearLayout.LayoutParams vertical_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        vertical_layout_params.gravity = Gravity.CENTER_VERTICAL;
                        vertical_layout.setLayoutParams(vertical_layout_params);
                        vertical_layout.setOrientation(LinearLayout.VERTICAL);

                        size = getPixelValueOfDP(this, 100);
                        TextView client_name_view = new TextView(this);
                        LinearLayout.LayoutParams client_name_view_params = new LinearLayout.LayoutParams(size, LinearLayout.LayoutParams.WRAP_CONTENT);
                        client_name_view.setLayoutParams(client_name_view_params);
                        client_name_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                        client_name_view.setTextColor(getResources().getColor(R.color.name_color));
                        client_name_view.setText(client_name);

                        TextView client_income_view = new TextView(this);
                        LinearLayout.LayoutParams client_income_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        client_income_view.setLayoutParams(client_income_view_params);
                        client_income_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        client_income_view.setText("" + client_income);

                        LinearLayout horizontal_layout2 = new LinearLayout(this);
                        LinearLayout.LayoutParams horizontal_layout2_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        horizontal_layout2_params.gravity = Gravity.CENTER_VERTICAL;
                        size = getPixelValueOfDP(this, 6);
                        horizontal_layout2_params.setMargins(size, size, size, size);
                        horizontal_layout2.setLayoutParams(horizontal_layout2_params);

                        TextView client_time_view = new TextView(this);
                        LinearLayout.LayoutParams client_time_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        client_time_view_params.gravity = Gravity.CENTER_VERTICAL;
                        client_time_view.setLayoutParams(client_time_view_params);
                        client_time_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                        client_time_view.setText(client_time);

                        int client_income_view_color = 0; // !!!
                        switch (client_income_state) {
                            case STATE_UNSIGNED:
                                client_income_view_color = getResources().getColor(R.color.journal_income_unsigned_color);
                                break;
                            case STATE_SIGNED:
                                client_income_view_color = getResources().getColor(R.color.journal_income_signed_color);
                                break;
                            case STATE_ABSENT:
                                client_income_view_color = getResources().getColor(R.color.journal_income_absent_color);
                                break;
                        }

                        client_income_view.setTextColor(client_income_view_color);
                        client_time_view.setTextColor(client_income_view_color);

                        View space = new View(this);
                        LinearLayout.LayoutParams space_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                        space.setLayoutParams(space_params);

                        ImageButton check_mark_button = new ImageButton(this);
                        size = getPixelValueOfDP(this, 60);
                        LinearLayout.LayoutParams check_mark_button_params = new LinearLayout.LayoutParams(size, size);
                        size = getPixelValueOfDP(this, 10);
                        check_mark_button_params.setMargins(0, 0, size, 0);
                        check_mark_button.setLayoutParams(check_mark_button_params);
                        check_mark_button.setBackgroundResource(R.drawable.check_mark);

                        ImageButton cancel_button = new ImageButton(this);
                        size = getPixelValueOfDP(this, 60);
                        LinearLayout.LayoutParams cancel_button_params = new LinearLayout.LayoutParams(size, size);
                        cancel_button.setLayoutParams(cancel_button_params);
                        cancel_button.setBackgroundResource(R.drawable.cancel);

                        check_mark_button.setOnClickListener(view -> {
                            coachAppOpenHelper.updateIncomeStateById(STATE_SIGNED, client_income_id);
                            client_time_view.setTextColor(getResources().getColor(R.color.journal_income_signed_color));
                            client_income_view.setTextColor(getResources().getColor(R.color.journal_income_signed_color));
                            check_mark_button.setVisibility(View.INVISIBLE);
                            cancel_button.setVisibility(View.INVISIBLE);
                        });
                        cancel_button.setOnClickListener(view -> {
                            coachAppOpenHelper.updateIncomeStateById(STATE_ABSENT, client_income_id);
                            client_time_view.setTextColor(getResources().getColor(R.color.journal_income_absent_color));
                            client_income_view.setTextColor(getResources().getColor(R.color.journal_income_absent_color));
                            check_mark_button.setVisibility(View.INVISIBLE);
                            cancel_button.setVisibility(View.INVISIBLE);
                        });

                        vertical_layout.addView(client_name_view);
                        vertical_layout.addView(client_income_view);
                        horizontal_layout2.addView(client_time_view);
                        horizontal_layout2.addView(space);
                        if (client_income_state.equals(STATE_UNSIGNED)) {
                            horizontal_layout2.addView(check_mark_button);
                            horizontal_layout2.addView(cancel_button);
                        }
                        horizontal_layout.addView(vertical_layout);
                        horizontal_layout.addView(horizontal_layout2);
                        later_day_layout.addView(horizontal_layout);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
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

    private String parseDateToUa(String date) {
        String day = date.split("\\.")[0];
        String month = date.split("\\.")[1];
        switch (month) {
            case "1":
                return day + " січня";
            case "2":
                return day + " лютого";
            case "3":
                return day + " березня";
            case "4":
                return day + " квітня";
            case "5":
                return day + " травня";
            case "6":
                return day + " червня";
            case "7":
                return day + " липня";
            case "8":
                return day + " серпня";
            case "9":
                return day + " вересня";
            case "10":
                return day + " жовтня";
            case "11":
                return day + " листопада";
            case "12":
                return day + " грудня";
        }
        return "error";
    }

    public static int getPixelValueOfDP(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }
}