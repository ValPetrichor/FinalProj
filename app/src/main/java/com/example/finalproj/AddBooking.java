package com.example.finalproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Calendar;

public class AddBooking extends AppCompatActivity {
    Button btnView, btnBook;
    EditText txtRestaurant, txtName, txtrdate, txtNumPpl, txtContact;
    ListView ViewRecord;
    DatabaseHandlerBooking db;
    public static int SID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            db = new DatabaseHandlerBooking(this);
            Toast.makeText(getApplicationContext(), "Database Successfully Connected!!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        // finds view objects given its ID
        ViewRecord = (ListView) findViewById(R.id.lvBooking);
        btnBook = (Button) findViewById(R.id.btnBook);
        btnView = (Button) findViewById(R.id.btnView);
        txtRestaurant = (EditText) findViewById(R.id.txtRestaurant);
        txtName = (EditText) findViewById(R.id.txtName);
        txtNumPpl = (EditText) findViewById(R.id.txtNumPpl);
        txtrdate = (EditText) findViewById(R.id.txtDate);
        txtContact = (EditText) findViewById(R.id.txtContact);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Reg_Data();
        // We use a datepicker which is a control that will allow users to select the date by a day, month and year.
        txtrdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddBooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        txtrdate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        // Redirects the user to the list view of registered members
        btnView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(AddBooking.this, ViewBooking.class);
                AddBooking.this.startActivity(myIntent);
            }
        });

    }
    // Allows the user to input data and store the data into the database
    private void Reg_Data() {
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SID == 0) {
                    Booking member = null;
                    try {
                        member = new Booking(0, txtRestaurant.getText().toString(),
                                txtrdate.getText().toString(),
                                txtName.getText().toString(),
                                txtNumPpl.getText().toString(),
                                txtContact.getText().toString());
                    } catch (Exception ex) {

                        Toast.makeText(AddBooking.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    DatabaseHandlerBooking ConnectDB = new DatabaseHandlerBooking(AddBooking.this);
                    ConnectDB.addRecord(member);
                    Toast.makeText(AddBooking.this, "Reservation Recorded\n", Toast.LENGTH_LONG).show();

                    txtRestaurant.setText("");
                    txtrdate.setText("");
                    txtName.setText("");
                    txtNumPpl.setText("");
                    txtContact.setText("");
                }

            }


        });
    }


}