package com.example.mad_practical_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    public static final String TABLE_USERS = "users";

    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FOLLOW = "followed";
    boolean follow = true;

    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_FOLLOW + " INTEGER" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUsers() {
        for (int i = 0 ; i < 20 ; i++){
            User user = new User();

            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, i);
            values.put(COLUMN_NAME, user.name);
            values.put(COLUMN_DESCRIPTION, user.description);
            values.put(COLUMN_FOLLOW, (user.followed ? 1 : 0));

            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_USERS, null, values);
            db.close();
        }
    }

    public ArrayList<User> getUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<User> users = new ArrayList<User>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()){
            if (cursor.getInt(3) == 1){
                follow = true;

            }else {
                follow = false;
            }

            User user = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(0), follow);
            users.add(user);
        }

        cursor.close();
        return users;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, user.id);
        cv.put(COLUMN_NAME, user.name);
        cv.put(COLUMN_DESCRIPTION, user.description);
        cv.put(COLUMN_FOLLOW, (user.followed ? 1 : 0));

        db.update(TABLE_USERS, cv, "_id = " + user.id, null);
    }

    public User updateUserOnClick(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_NAME + " = \"" + username + "\"", null);
        cursor.moveToFirst();
        User user = new User(cursor.getString(1), cursor.getString(2), cursor.getInt(0), (cursor.getInt(3) == 1 ? true : false));

        return user;
    }
}
