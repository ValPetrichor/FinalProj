package com.example.finalproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerBooking extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Reservation";
    private static final String TABLE_Reservation = "tb_reservation";
    private static final String KEY_ID = "id";
    private static final String KEY_Full_NAME = "name";
    private static final String KEY_Restaurant = "restaurant";
    private static final String KEY_ReserveDate = "rdate";
    private static final String KEY_Contact = "contact";
    private static final String KEY_NumPeople = "numpeople";

    public DatabaseHandlerBooking(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESERVATION_TABLE = "CREATE TABLE " + TABLE_Reservation
                + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_Restaurant + " TEXT, "
                + KEY_ReserveDate  + " TEXT, "
                + KEY_Full_NAME + " TEXT, "
                + KEY_NumPeople + " TEXT, "
                + KEY_Contact + " TEXT" + ")";
        db.execSQL(CREATE_RESERVATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Reservation);

        // Create tables again
        onCreate(db);
    }

    // Adds records to the database. Content values has (column name, data) as parameters.
    public boolean addRecord(Booking record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Restaurant, record.get_restaurant());
        values.put(KEY_ReserveDate, record.get_rdate());
        values.put(KEY_Full_NAME, record.get_name());
        values.put(KEY_NumPeople, record.get_numpeople());
        values.put(KEY_Contact, record.get_contact());

        long insert =  db.insert(TABLE_Reservation, null, values);
        if (insert==-1) return false;
        else return true;
    }

    public List<Booking> viewAllData(){
        List<Booking> returnAllData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor CheckRecord = db.rawQuery("SELECT * FROM " + TABLE_Reservation, null);

        if(CheckRecord.getCount()>0){
            while(CheckRecord.moveToNext()){
                int id = CheckRecord.getInt(0);
                String restaurant = CheckRecord.getString(1);
                String rdate = CheckRecord.getString(2);
                String fullName = CheckRecord.getString(3);
                String numpeople = CheckRecord.getString(4);
                String contact = CheckRecord.getString(5);

                Booking allMember = new Booking(id,restaurant,rdate,fullName,numpeople,contact);
                returnAllData.add(allMember);
            }
        }
        else{
        }
        CheckRecord.close();
        db.close();
        return returnAllData;
    }
    // Method to delete a members' record
    public boolean DeleteRecord(Booking member){

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor strQuery = db.rawQuery("DELETE FROM " + TABLE_Reservation + " WHERE " + KEY_ID + " = " + member.getID(),null);

        if(strQuery.moveToFirst()) return true;
        else return false;
    }
    public boolean UpdateRecord(String id, String restaurant, String rdate, String name, String numpeople , String contact){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID,id);
        values.put(KEY_Restaurant,restaurant);
        values.put(KEY_ReserveDate,rdate);
        values.put(KEY_Full_NAME,name);
        values.put(KEY_NumPeople,numpeople);
        values.put(KEY_Contact,contact);

        db.update(TABLE_Reservation,values,"id=?", new String[]{String.valueOf(id)});
        return true;
    }
}