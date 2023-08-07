package com.example.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlHelper extends SQLiteOpenHelper {


    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_WIN_SCORE = "WIN_SCORE";
    public static final String COLUMN_LOSE_SCORE = "LOSE_SCORE";
    public static final String COLUMN_ID = "ID";

    public SqlHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " +
                COLUMN_WIN_SCORE + " INTEGER, " + COLUMN_LOSE_SCORE + " INTEGER)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}


    @Override
    public void onOpen (SQLiteDatabase sqLiteDatabase) {}


    public void addOne(UserModel user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, user.getUserName());
        cv.put(COLUMN_WIN_SCORE, user.getWins());
        cv.put(COLUMN_LOSE_SCORE, user.getLoses());

        db.insert(USER_TABLE, null, cv);

    }

    public boolean validateUser(UserModel user) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean checkUser = false;
        String queryString = "SELECT " + COLUMN_USER_NAME + " FROM " + USER_TABLE
                + " WHERE " + COLUMN_USER_NAME + " = '" + user.userName + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() == 0) {
            checkUser = false;

        } else {
            checkUser = true;
        }

        return checkUser;

    }

    public boolean validateUserByUser(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean checkUser = false;
        String queryString = "SELECT " + COLUMN_USER_NAME + " FROM " + USER_TABLE
                + " WHERE " + COLUMN_USER_NAME + " = '" + userName + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.getCount() == 0) {
            checkUser = false;

        } else {
            checkUser = true;
        }

        return checkUser;

    }

    public UserModel selectOne(UserModel user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_ID + ", " + COLUMN_WIN_SCORE + ", " + COLUMN_LOSE_SCORE
                + " FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " = '" + user.userName + "'";
        Cursor cursor = db.rawQuery(queryString, null);

        String userUsername = null;
        int userID= 0,winScore= 0,loseScore = 0;
       if(cursor != null)
       {
           while(cursor.moveToNext())
           {
               userUsername = user.userName;
               userID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
               winScore = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WIN_SCORE));
               loseScore = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOSE_SCORE));
           }
       }

        return new UserModel(userID, userUsername, winScore, loseScore);
    }

    public boolean updateOne(UserModel user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WIN_SCORE, user.getWins());
        cv.put(COLUMN_LOSE_SCORE, user.getLoses());


        return db.update(USER_TABLE, cv, COLUMN_USER_NAME + "='"+ user.getUserName() + "'", null) > 0;
    }
}
