package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.ShareActionProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import at.markushi.ui.CircleButton;

public class ViewDiaryActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        TextView empty_diary_text_view = findViewById(R.id.empty_diary_text_view);

        TextView client_name_view = findViewById(R.id.client_name_view);

        EditText notes_edit_view = findViewById(R.id.notes_edit_view);

        LinearLayout notes_vertical_layout = findViewById(R.id.notes_vertical_layout);

        LinearLayout note_edit_layout = findViewById(R.id.note_edit_layout);

        CircleButton circleButton = findViewById(R.id.circle_button);
        circleButton.setOnClickListener(view -> {
            note_edit_layout.setVisibility(View.GONE);
            circleButton.setVisibility(View.GONE);
        });

        int records_count = 0;

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getNotesCursor();
            if (cursor.moveToFirst()) {
                do {
                    records_count++;
                    int id = cursor.getInt(0);
                    String client_name = cursor.getString(1);
                    String date = cursor.getString(2);
                    String notes = cursor.getString(3);

                    LinearLayout note_frame_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams note_frame_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            getPixelValueOfDP(this, 300));
                    int size = getPixelValueOfDP(this, 14);
                    note_frame_layout_params.gravity = Gravity.CENTER_HORIZONTAL;
                    note_frame_layout_params.setMargins(size, size, size, size);
                    note_frame_layout.setLayoutParams(note_frame_layout_params);
                    size = getPixelValueOfDP(this, 6);
                    note_frame_layout.setPadding(size, size, size, size);
                    note_frame_layout.setBackgroundResource(R.drawable.note_frame);
                    note_frame_layout.setOrientation(LinearLayout.VERTICAL);

                    PopupMenu popupMenu = new PopupMenu(this, note_frame_layout);

                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu2, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(menuItem -> {
                        switch (menuItem.getItemId()) {
                            case R.id.extra_button_send_client:
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT, notes);
                                Intent chosenIntent = Intent.createChooser(intent, "Надіслати");
                                startActivity(chosenIntent);
                                return true;
                            case R.id.extra_button_delete_client:
                                coachAppOpenHelper.deleteNote(id);
                                notes_vertical_layout.removeView(note_frame_layout);
                                return true;
                        }
                        return false;
                    });

                    note_frame_layout.setOnClickListener(view -> {
                        client_name_view.setText(client_name);
                        notes_edit_view.setText(notes);
                        circleButton.setVisibility(View.VISIBLE);
                        note_edit_layout.setVisibility(View.VISIBLE);
                    });

                    note_frame_layout.setOnLongClickListener(view -> {
                        popupMenu.show();
                        return false;
                    });

                    TextView note_date_view = new TextView(this);
                    LinearLayout.LayoutParams note_date_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    note_date_view_params.gravity = Gravity.CENTER_HORIZONTAL;
                    size = getPixelValueOfDP(this, 10);
                    note_date_view_params.setMargins(size, size, size, size);
                    note_date_view.setLayoutParams(note_date_view_params);
                    note_date_view.setMaxLines(1);
                    note_date_view.setTextColor(getResources().getColor(R.color.journal_date_color));
                    note_date_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    note_date_view.setText(date);

                    View separator1 = new View(this);
                    LinearLayout.LayoutParams separator1_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPixelValueOfDP(this, 1));
                    separator1_params.setMargins(size, 0, size, 0);
                    separator1.setLayoutParams(separator1_params);
                    separator1.setBackgroundColor(getResources().getColor(R.color.separator_color));

                    TextView note_view = new TextView(this);
                    LinearLayout.LayoutParams note_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, getPixelValueOfDP(this, 200));
                    note_view_params.setMargins(size, 0, size, 0);
                    note_view.setLayoutParams(note_view_params);
                    note_view.setVerticalScrollBarEnabled(true);
                    note_view.setText(notes);

                    View separator2 = new View(this);
                    LinearLayout.LayoutParams separator2_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPixelValueOfDP(this, 1));
                    separator2_params.setMargins(size, 0, size, 0);
                    separator2.setLayoutParams(separator2_params);
                    separator2.setBackgroundColor(getResources().getColor(R.color.separator_color));

                    TextView note_name_view = new TextView(this);
                    LinearLayout.LayoutParams note_name_view_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    note_name_view_params.gravity = Gravity.CENTER;
                    note_name_view.setLayoutParams(note_name_view_params);
                    note_name_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    note_name_view.setEllipsize(TextUtils.TruncateAt.END);
                    size = getPixelValueOfDP(this, 6);
                    note_name_view.setPadding(size, size, size, size);
                    note_name_view.setMaxLines(1);
                    note_name_view.setText(client_name);

                    note_frame_layout.addView(note_date_view);
                    note_frame_layout.addView(separator1);
                    note_frame_layout.addView(note_view);
                    note_frame_layout.addView(separator2);
                    note_frame_layout.addView(note_name_view);
                    notes_vertical_layout.addView(note_frame_layout);
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
            empty_diary_text_view.setVisibility(View.VISIBLE);
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