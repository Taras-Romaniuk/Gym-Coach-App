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
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class StatisticsActivity extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

//        GraphView signed_absent_stat = findViewById(R.id.signed_absent_stat);
//
//        Date current_date_date = new Date();
//        String current_date = sdf.format(current_date_date);
//        List<String> dates = new ArrayList<>();
//        List<Float> incomes = new ArrayList<>();
//
//        try {
//            CoachAppOpenHelper coachAppOpenHelper = new CoachAppOpenHelper(this);
//            SQLiteDatabase db = coachAppOpenHelper.getReadableDatabase();
//            Cursor cursor = coachAppOpenHelper.getIncomesASCCursor();
//            if (cursor.moveToFirst()) {
//                do {
//                    String date = cursor.getString(2).split(" at ")[0];
//                    float income = cursor.getFloat(4);
//                    String week_ago_date = sdf.format(new Date(current_date_date.getTime() - 7 * 24 * 60 * 60 * 1000));
//                    if (isBetweenDate(date, week_ago_date, current_date)) {
//                        dates.add(date);
//                        incomes.add(income);
//                    }
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            db.close();
//            if (dates.size() > 1) {
//                DataPoint[] dataPoints = new DataPoint[dates.size()];
//                for (int i = 0; i < dates.size(); i++) {
//                    dataPoints[i] = new DataPoint(i, incomes.get(i));
//                }
//                signed_absent_stat.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//                    int counter = 0;
//
//                    @Override
//                    public String formatLabel(double value, boolean isValueX) {
//                        if (isValueX) {
//                            return dates.get(counter++);
//                        } else {
//                            return super.formatLabel(value, isValueX);
//                        }
//                    }
//                });
//                LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(dataPoints);
//                signed_absent_stat.addSeries(series1);
//            }
//        } catch (SQLiteException e) {
//            Toast toast = Toast.makeText(this, "База даних недоступна",
//                    Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//
//    private boolean isBetweenDate(String d, String min, String max) {
//        try {
//            Date d_date = sdf.parse(d);
//            Date min_date = sdf.parse(min);
//            Date max_date = sdf.parse(max);
//            return d_date.after(min_date) && d_date.before(max_date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    }
}