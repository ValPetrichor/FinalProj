package com.example.finalproj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.finalproj.Booking;
import com.example.finalproj.DatabaseHandlerBooking;

public class ViewBooking extends AppCompatActivity {
    DatabaseHandlerBooking DbRecord;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private ListView ViewRecord;
    public static int SID=0;
    ArrayAdapter MemberArray;
    Context context;
    Button btnReg;
    SearchView svRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);
        DbRecord = new DatabaseHandlerBooking(this);
        ViewRecord = (ListView)findViewById(R.id.lvBooking);
        svRecord = findViewById(R.id.searchrecord);
        listItem = new ArrayList<>();
        btnReg = (Button) findViewById(R.id.btnBook);

        DatabaseHandlerBooking db = new DatabaseHandlerBooking(ViewBooking.this);
        List<Booking> viewAllRec = db.viewAllData();

        MemberArray = new ArrayAdapter<Booking>(ViewBooking.this,android.R.layout.simple_list_item_1, viewAllRec);
        ViewRecord.setAdapter(MemberArray);

        // Lets user choose to either update or delete
        ViewRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Booking clickID;
            boolean isActive;
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickID = (Booking) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"Clicked Value: "+clickID.getID(),Toast.LENGTH_LONG).show();
                AlertDialog.Builder confirmationAlert= new AlertDialog.Builder(ViewBooking.this);
                confirmationAlert.setMessage("Do you want to Update or delete ?").setCancelable(false)
                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            // Retrieves data based on ID selected for update
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    SQLiteDatabase db = getApplicationContext().openOrCreateDatabase("Reservation", Context.MODE_PRIVATE, null);
                                    Cursor cursor = db.rawQuery("SELECT * FROM tb_reservation WHERE id=" + clickID.getID(), null);

                                    while (cursor.moveToNext()) {
                                        int index1 = cursor.getColumnIndex("id");
                                        int index2 = cursor.getColumnIndex("restaurant");
                                        int index3 = cursor.getColumnIndex("rdate");
                                        int index4 = cursor.getColumnIndex("name");
                                        int index5 = cursor.getColumnIndex("numpeople");
                                        int index6 = cursor.getColumnIndex("contact");

                                        String id = cursor.getString(index1);
                                        String restaurant = cursor.getString(index2);
                                        String rdate = cursor.getString(index3);
                                        String name = cursor.getString(index4);
                                        String numpeople = cursor.getString(index5);
                                        String contact = cursor.getString(index6);

                                        Intent myIntent = new Intent(ViewBooking.this, Edit_Del_Booking.class);

                                        myIntent.putExtra("id", id); //Optional parameters
                                        myIntent.putExtra("restaurant", restaurant); //Optional parameters
                                        myIntent.putExtra("rdate", rdate); //Optional parameters
                                        myIntent.putExtra("name", name); //Optional parameters
                                        myIntent.putExtra("numpeople", numpeople); //Optional parameters
                                        myIntent.putExtra("contact", contact); //Optional parameters

                                        startActivity(myIntent);
                                        finish();
                                    }

                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }

                        })
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            // Delete selected ID
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbRecord.DeleteRecord(clickID);
                                Toast.makeText(getApplicationContext(),"Deleted successfully",Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alert = confirmationAlert.create();
                alert.setTitle("Member Delete/Update");
                alert.show();
            }
        });

        // For searching records
        // user action within the searchview
        svRecord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // Searches after text submit
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            // View changes when text changes
            public boolean onQueryTextChange(String newText) {
                try {
                    MemberArray.getFilter().filter(newText);
                    return false;
                }catch (Exception e){
                    DatabaseHandlerBooking ConnectDB = new DatabaseHandlerBooking(ViewBooking.this);
                    List<Booking> viewAllRec = ConnectDB.viewAllData();
                    MemberArray = new ArrayAdapter<Booking>(ViewBooking.this,android.R.layout.simple_list_item_1, viewAllRec);
                    ViewRecord.setAdapter(MemberArray);
                    return false;
                }
            }
        });
    }



}