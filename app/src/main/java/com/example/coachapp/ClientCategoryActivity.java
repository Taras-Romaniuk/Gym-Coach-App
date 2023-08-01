package com.example.coachapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClientCategoryActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_category);

        LinearLayout clients_layout = findViewById(R.id.clients_layout);

        try {
            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
            Cursor cursor = coachAppOpenHelper.getClientsCursor(); // !!!
            if (cursor.moveToFirst()) {
                do {
                    String client_name = cursor.getString(0);
                    LinearLayout client_layout = new LinearLayout(this);
                    LinearLayout.LayoutParams client_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    client_layout.setLayoutParams(client_layout_params);
                    client_layout.setClickable(true);
                    client_layout.setFocusable(true);
                    int size = getPixelValueOfDP(this, 16);
                    client_layout.setPadding(size, size, size, size);

                    client_layout.setOnClickListener(view -> {
                        Intent intent = new Intent(this, ClientActivity.class);
                        intent.putExtra("client_name_extra", client_name);
                        startActivity(intent);
                    });
                    //
                    TypedValue typedValue = new TypedValue();

                    // I used getActivity() as if you were calling from a fragment.
                    // You just want to call getTheme() on the current activity, however you can get it
                    this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);

                    // it's probably a good idea to check if the color wasn't specified as a resource
                    if (typedValue.resourceId != 0) {
                        client_layout.setBackgroundResource(typedValue.resourceId);
                    } else {
                        // this should work whether there was a resource id or not
                        client_layout.setBackgroundColor(typedValue.data);
                    }
                    //
                    // ...
                    ImageView client_photo = new ImageView(this);
                    client_photo.setBackgroundResource(R.drawable.client);
                    size = getPixelValueOfDP(this, 64);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
                    client_photo.setLayoutParams(layoutParams);
                    //
                    TextView client_name_view = new TextView(this);
                    client_name_view.setText(client_name);
                    client_name_view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    client_name_view.setGravity(Gravity.START);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(getPixelValueOfDP(this, 20), 0, 0, 0);
                    params.gravity = Gravity.CENTER;
                    client_name_view.setLayoutParams(params);
                    // ...
                    //
                    ImageButton extra_button = new ImageButton(this);
                    size = getPixelValueOfDP(this, 25);
                    LinearLayout.LayoutParams extra_button_params = new LinearLayout.LayoutParams(size, size);
                    extra_button_params.setMargins(0, 0, getPixelValueOfDP(this, 20), 0);
                    extra_button_params.gravity = Gravity.CENTER_VERTICAL;
                    extra_button.setLayoutParams(extra_button_params);
                    extra_button.setBackgroundResource(R.drawable.dots);

                    PopupMenu popupMenu = new PopupMenu(this, extra_button);

                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(menuItem -> {
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.extra_button_new_income:
                                intent = new Intent(this, NewIncomeActivity.class);
                                intent.putExtra("client_name_extra", client_name);
                                startActivity(intent);
                                return true;
                            case R.id.extra_button_new_note:
                                intent = new Intent(this, NewNote.class);
                                intent.putExtra("client_name_extra", client_name);
                                startActivity(intent);
                                return true;
                            case R.id.extra_button_delete_client:
                                coachAppOpenHelper.deleteClient(client_name);
                                clients_layout.removeView(client_layout);
                                return true;
                        }
                        return false;
                    });

                    extra_button.setOnClickListener(view -> popupMenu.show());

                    //
                    client_layout.addView(extra_button);
                    client_layout.addView(client_photo);
                    client_layout.addView(client_name_view);
                    clients_layout.addView(client_layout);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База даних недоступна",
                    Toast.LENGTH_SHORT);
            toast.show();
        }

        LinearLayout new_client_layout = findViewById(R.id.new_client_layout);
        new_client_layout.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewClientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
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