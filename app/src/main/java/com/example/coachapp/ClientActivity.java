package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends AppCompatActivity {

    private static final String STATE_UNSIGNED = "unsigned";
    private static final String STATE_SIGNED = "signed";
    private static final String STATE_ABSENT = "absent";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        TextView notes_history_text = findViewById(R.id.notes_history_text);
        TextView incomes_history_text = findViewById(R.id.incomes_history_text);
        TextView client_incomes_total = findViewById(R.id.client_incomes_total);

        FrameLayout client_notes = findViewById(R.id.client_notes);
        LinearLayout client_incomes_layout = findViewById(R.id.client_incomes_layout);

        String client_name_extra = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client_name_extra = extras.getString("client_name_extra");
        }

        TextView client_name_text_view = findViewById(R.id.client_name);
        client_name_text_view.setText(client_name_extra);

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            int notes_records_count = coachAppOpenHelper.getClientNotesCount(client_name_extra);
            notes_history_text.setText(getResources().getString(R.string.notes) + "(" + notes_records_count + ")");
            db.close();
            if (notes_records_count != 0) {
                String final_Client_name_extra = client_name_extra;
                client_notes.setOnClickListener(view -> {
                    Intent intent = new Intent(this, ClientNotesActivity.class);
                    intent.putExtra("client_name_extra", final_Client_name_extra);
                    startActivity(intent);
                });
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        int incomes_records_count = 0;
        float client_incomes_sum = 0;

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getClientIncomesCursor(client_name_extra);
            if (cursor.moveToFirst()) {
                do {
                    incomes_records_count++;
                    String client_date = cursor.getString(0);
                    String client_income_state = cursor.getString(1);
                    float client_income = cursor.getFloat(2);
                    if (client_income_state.equals(STATE_SIGNED)) client_incomes_sum += client_income;

                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    linearLayout.setLayoutParams(linearLayoutParams);

                    TextView textView1 = new TextView(this);
                    textView1.setText(Float.toString(client_income));

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

                    textView1.setTextColor(client_income_view_color);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    LinearLayout.LayoutParams textView1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView1Params.setMargins(0, 0, getPixelValueOfDP(this, 20), 0);
                    textView1.setLayoutParams(textView1Params);

                    TextView textView2 = new TextView(this);
                    textView2.setText(client_date);
                    textView2.setTextColor(getResources().getColor(R.color.journal_date_color));
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    LinearLayout.LayoutParams textView2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    textView2.setLayoutParams(textView2Params);

                    linearLayout.addView(textView1);
                    linearLayout.addView(textView2);
                    client_incomes_layout.addView(linearLayout);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        incomes_history_text.setText(getResources().getString(R.string.client_incomes) + "(" + incomes_records_count + ")");
        client_incomes_total.setText(getResources().getString(R.string.total) + " " + client_incomes_sum);
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