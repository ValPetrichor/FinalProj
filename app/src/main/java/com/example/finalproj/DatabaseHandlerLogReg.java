package com.example.finalproj;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerLogReg extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registration";
    public static final String COL_1="ID";
    public static final String COL_2="Name";
    public static final String COL_3="Phone";
    public static final String COL_4="Gmail";
    public static final String COL_5="Password";
    public DatabaseHandlerLogReg(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Phone TEXT,Gmail TEXT,Password TEXT)");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }


    public Cursor getListContents() {
        return null;
    }

    public Integer deleteData(String toString) {
        return null;
    }

    public boolean updateData(String name, String Name, String Address, String Reservation, String Gender, String Email, String Contact) {
        return false;
    }

    public boolean addPerson(int Id, String Name, String Address, String Reservation, String Gender, String Email, String Contact) {
        return false;
    }
}

