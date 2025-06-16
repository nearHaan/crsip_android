package com.duk.crsipandroid.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "crispDB";
    private static String TABLE_USERS = "Users";
    private static String COLUMN_USERS_ID = "id";
    private static String COLUMN_USERS_NAME = "name";
    private static String COLUMN_USERS_USERNAME = "username";
    private static String COLUMN_USERS_EMAIL = "email";
    private static String COLUMN_USERS_PHONE = "phone";
    private static String COLUMN_USERS_PASSWORD = "password";

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERS_NAME + " TEXT,"
                + COLUMN_USERS_USERNAME + " TEXT,"
                + COLUMN_USERS_EMAIL + " TEXT,"
                + COLUMN_USERS_PHONE + " NUMERIC,"
                + COLUMN_USERS_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(String name, String username, String email, String phone, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERS_NAME, name);
        values.put(COLUMN_USERS_USERNAME, username);
        values.put(COLUMN_USERS_EMAIL, email);
        values.put(COLUMN_USERS_PHONE, phone);
        values.put(COLUMN_USERS_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public boolean validateUser(String phone_number, String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "+COLUMN_USERS_PASSWORD+" FROM "+TABLE_USERS+" WHERE "+COLUMN_USERS_PHONE+" = ?", new String[]{phone_number});
        if (result.moveToFirst()){
            if (result.getString(result.getColumnIndex(COLUMN_USERS_PASSWORD)).equals(password)){
                result.close();
                return true;
            }
        }
        result.close();
        return false;
    }

    public boolean ifUsernameUnique(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE "+COLUMN_USERS_USERNAME+" = ?", new String[]{username});
        if (!result.moveToFirst()){
            return true;
        }
        return false;
    }

    public boolean ifPhoneUnique(String phone){
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE "+COLUMN_USERS_PHONE+" = ?", new String[]{phone});
        if (!result.moveToFirst()){
            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public void getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_USERS_PHONE));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_USERS_PASSWORD));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}