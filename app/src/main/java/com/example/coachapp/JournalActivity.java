package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JournalActivity extends AppCompatActivity {

    private static final String STATE_UNSIGNED = "unsigned";
    private static final String STATE_SIGNED = "signed";
    private static final String STATE_ABSENT = "absent";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        TextView empty_journal_text_view = findViewById(R.id.empty_journal_text_view);

        LinearLayout incomes_layout = findViewById(R.id.incomes_layout);

        int records_count = 0;

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getIncomesCursor();
            if (cursor.moveToFirst()) {
                do {
                    records_count++;
                    int client_income_id = cursor.getInt(0);
                    String client_name = cursor.getString(1);
                    String client_date = cursor.getString(2);
                    String client_income_state = cursor.getString(3);
                    float client_income = cursor.getFloat(4);

                    LinearLayout whole_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams whole_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    whole_layout.setLayoutParams(whole_layout_params);
                    whole_layout.setClickable(true);
                    whole_layout.setFocusable(true);
                    whole_layout.setOrientation(LinearLayout.VERTICAL);
                    int size = getPixelValueOfDP(this, 10);
                    whole_layout.setPadding(size, size, size, size);
                    TypedValue typedValue = new TypedValue();

                    this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);

                    if (typedValue.resourceId != 0) {
                        whole_layout.setBackgroundResource(typedValue.resourceId);
                    } else {
                        whole_layout.setBackgroundColor(typedValue.data);
                    }

                    TextView date_text_view = new TextView(this);
                    date_text_view.setText(client_date);
                    date_text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    date_text_view.setTextColor(getResources().getColor(R.color.journal_date_color));
                    date_text_view.setTypeface(null, Typeface.BOLD);
                    LinearLayout.LayoutParams date_text_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    date_text_view_params.gravity = Gravity.CENTER;
                    date_text_view.setLayoutParams(date_text_view_params);

                    LinearLayout horizontal_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams horizontal_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    horizontal_layout.setLayoutParams(horizontal_layout_params);
                    horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout income_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams income_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    income_layout.setLayoutParams(income_layout_params);
                    income_layout.setOrientation(LinearLayout.VERTICAL);

                    TextView name_text_view = new TextView(this);
                    name_text_view.setText(client_name);
                    name_text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                    LinearLayout.LayoutParams name_text_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    name_text_view.setLayoutParams(name_text_view_params);

                    TextView income_text_view = new TextView(this);
                    income_text_view.setText(Float.toString(client_income));
                    income_text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

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

                    income_text_view.setTextColor(client_income_view_color);
                    LinearLayout.LayoutParams income_text_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    income_text_view.setLayoutParams(income_text_view_params);

                    View space = new View(this);
                    LinearLayout.LayoutParams space_params = new LinearLayout.LayoutParams(0, 0, 1.0f);
                    space.setLayoutParams(space_params);

                    ImageButton check_mark_button = new ImageButton(this);
                    size = getPixelValueOfDP(this, 60);
                    LinearLayout.LayoutParams check_mark_button_params = new LinearLayout.LayoutParams(size, size);
                    check_mark_button_params.setMargins(0, 0, getPixelValueOfDP(this, 20), 0);
                    check_mark_button.setLayoutParams(check_mark_button_params);
                    check_mark_button.setBackgroundResource(R.drawable.check_mark);

                    ImageButton cancel_button = new ImageButton(this);
                    LinearLayout.LayoutParams cancel_button_params = new LinearLayout.LayoutParams(size, size);
                    cancel_button_params.setMargins(0, 0, getPixelValueOfDP(this, 20), 0);
                    cancel_button.setLayoutParams(cancel_button_params);
                    cancel_button.setBackgroundResource(R.drawable.cancel);

                    check_mark_button.setOnClickListener(view -> {
                        coachAppOpenHelper.updateIncomeStateById(STATE_SIGNED, client_income_id);
                        income_text_view.setTextColor(getResources().getColor(R.color.journal_income_signed_color));
                        check_mark_button.setVisibility(View.INVISIBLE);
                        cancel_button.setVisibility(View.INVISIBLE);
                    });
                    cancel_button.setOnClickListener(view -> {
                        coachAppOpenHelper.updateIncomeStateById(STATE_ABSENT, client_income_id);
                        income_text_view.setTextColor(getResources().getColor(R.color.journal_income_absent_color));
                        check_mark_button.setVisibility(View.INVISIBLE);
                        cancel_button.setVisibility(View.INVISIBLE);
                    });

                    income_layout.addView(name_text_view);
                    income_layout.addView(income_text_view);
                    horizontal_layout.addView(income_layout);
                    horizontal_layout.addView(space);
                    if (client_income_state.equals(STATE_UNSIGNED)) {
                        horizontal_layout.addView(check_mark_button);
                        horizontal_layout.addView(cancel_button);
                    }
                    whole_layout.addView(date_text_view);
                    whole_layout.addView(horizontal_layout);
                    incomes_layout.addView(whole_layout);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        if (records_count == 0) {
            empty_journal_text_view.setVisibility(View.VISIBLE);
        }
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