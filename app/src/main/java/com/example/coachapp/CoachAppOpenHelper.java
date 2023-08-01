package com.example.coachapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoachAppOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "coach_app";
    private static final int DB_VERSION = 1;
    private static final String TABLE_CLIENT = "CLIENT";
    private static final String TABLE_CLIENT_ID = "_id";
    private static final String TABLE_CLIENT_NAME = "NAME";
    private static final String TABLE_DIARY = "DIARY";
    private static final String TABLE_DIARY_ID = "_id";
    private static final String TABLE_DIARY_NAME = "NAME";
    private static final String TABLE_DIARY_DATE = "DATE";
    private static final String TABLE_DIARY_NOTES = "NOTES";
    private static final String TABLE_INCOME = "INCOME";
    private static final String TABLE_INCOME_ID = "_id";
    private static final String TABLE_INCOME_NAME = "NAME";
    private static final String TABLE_INCOME_DATE = "DATE";
    private static final String TABLE_INCOME_STATE = "STATE";
    private static final String TABLE_INCOME_INCOME = "INCOME";

    CoachAppOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CLIENT + "(" +
                TABLE_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_CLIENT_NAME + " TEXT);");
        db.execSQL("CREATE TABLE " + TABLE_DIARY + "(" +
                TABLE_DIARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_DIARY_NAME + " TEXT," +
                TABLE_DIARY_DATE + " TEXT," +
                TABLE_DIARY_NOTES + " TEXT);");
        db.execSQL("CREATE TABLE " + TABLE_INCOME + "(" +
                TABLE_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE_INCOME_NAME + " TEXT," +
                TABLE_INCOME_DATE + " TEXT," +
                TABLE_INCOME_STATE + " TEXT," +
                TABLE_INCOME_INCOME + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addClient(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_CLIENT_NAME, name);

        db.insert(TABLE_CLIENT, null, values);
        db.close();
    }

    public Cursor getNotesCursor() {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_DIARY_ID + "," + TABLE_DIARY_NAME + "," + TABLE_DIARY_DATE + "," + TABLE_DIARY_NOTES + " FROM " + TABLE_DIARY + " ORDER BY " + TABLE_DIARY_DATE + " DESC", null);
    }

    public Cursor getClientsCursor() {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_CLIENT_NAME + " FROM " + TABLE_CLIENT + " ORDER BY " + TABLE_CLIENT_NAME, null);
    }

    public Cursor getIncomesCursor() {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_INCOME_ID + "," + TABLE_INCOME_NAME + "," + TABLE_INCOME_DATE + "," + TABLE_INCOME_STATE + "," + TABLE_INCOME_INCOME + " FROM " + TABLE_INCOME + " ORDER BY " + TABLE_INCOME_DATE + " DESC", null);
    }

    public Cursor getIncomesASCCursor() {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_INCOME_ID + "," + TABLE_INCOME_NAME + "," + TABLE_INCOME_DATE + "," + TABLE_INCOME_STATE + "," + TABLE_INCOME_INCOME + " FROM " + TABLE_INCOME + " ORDER BY " + TABLE_INCOME_DATE, null);
    }

    public int getClientNotesCount(String name) {
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_DIARY + " WHERE " + TABLE_DIARY_NAME + "='" + name + "'", null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getClientsTodayCursor(String date) {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_INCOME_ID + "," + TABLE_INCOME_NAME + "," + TABLE_INCOME_DATE + "," + TABLE_INCOME_STATE
                + "," + TABLE_INCOME_INCOME + " FROM " + TABLE_INCOME + " WHERE " + TABLE_INCOME_DATE + " LIKE '" + date + "%'", null);
    }

    public int getClientsTodayCount(String date) {
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_INCOME + " WHERE " + TABLE_INCOME_DATE + " LIKE '" + date + "%'", null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getClientNotesCursor(String name) {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_DIARY_ID + "," + TABLE_DIARY_DATE + "," + TABLE_DIARY_NOTES + " FROM " + TABLE_DIARY + " WHERE " + TABLE_DIARY_NAME + "=" + "'" + name + "'" + " ORDER BY " + TABLE_DIARY_DATE + " DESC", null);
    }

    public Cursor getClientIncomesCursor(String name) {
        return this.getWritableDatabase().rawQuery("SELECT " + TABLE_INCOME_DATE + "," + TABLE_INCOME_STATE + "," + TABLE_INCOME_INCOME + " FROM " + TABLE_INCOME + " WHERE " + TABLE_INCOME_NAME + "=" + "'" + name + "'" + " ORDER BY " + TABLE_INCOME_DATE + " DESC", null);
    }

    public void insertNoteInDB(String name, String date, String notes) {
        this.getWritableDatabase().execSQL("INSERT INTO " + TABLE_DIARY + "(" +
                TABLE_DIARY_NAME + ',' +
                TABLE_DIARY_DATE + ',' +
                TABLE_DIARY_NOTES + ") VALUES ('" + name + "', '" + date + "', '" + notes + "')");
    }

    public void insertIncomeInDB(String name, String date, String state, float income) {
        this.getWritableDatabase().execSQL("INSERT INTO " + TABLE_INCOME + "(" +
                TABLE_INCOME_NAME + ',' +
                TABLE_INCOME_DATE + ',' +
                TABLE_INCOME_STATE + ',' +
                TABLE_INCOME_INCOME + ") VALUES ('" + name + "', '" + date + "', '" + state +  "', '" + income + "')");
    }

    public void updateIncomeStateById(String state, int id) {
        this.getWritableDatabase().execSQL("UPDATE " + TABLE_INCOME + " SET " + TABLE_INCOME_STATE + " = '" + state + "' WHERE " + TABLE_INCOME_ID + " = '" + id + "'");
    }

    public void deleteClient(String name) {
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLE_CLIENT + " WHERE " + TABLE_CLIENT_NAME + "=" + "'" + name + "'");
    }

    public void deleteNote(int id) {
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLE_DIARY + " WHERE " + TABLE_DIARY_ID + "=" + id);
    }
}
