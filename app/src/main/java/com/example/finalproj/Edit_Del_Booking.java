package com.example.finalproj;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Edit_Del_Booking extends AppCompatActivity {
    Button btnUpdate, btnCancel;
    public static int SID=0;
    DatabaseHandlerBooking db;
    EditText txtRestaurant, txtrdate, txtName, txtNumPeople, txtContact, txtID;
    String selectedID,restaurant,rdate,name,numpeople,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_del_booking);

        db = new DatabaseHandlerBooking(this);
        // finds view objects given its ID
        txtID = (EditText)findViewById(R.id.txtID);
        txtRestaurant=(EditText)findViewById(R.id.txtRestaurant);
        txtrdate=(EditText)findViewById(R.id.txtDate);
        txtName = (EditText)findViewById(R.id.txtName);
        txtNumPeople=(EditText)findViewById(R.id.txtNumPpl);
        txtContact=(EditText)findViewById(R.id.txtContact);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        String a = txtID.getText().toString();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        txtrdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Edit_Del_Booking.this, new DatePickerDialog.OnDateSetListener() {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Edit_Del_Booking.this, ViewBooking.class);
                Edit_Del_Booking.this.startActivity(myIntent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.UpdateRecord(txtID.getText().toString(),
                        txtRestaurant.getText().toString(),
                        txtrdate.getText().toString(),
                        txtName.getText().toString(),
                        txtNumPeople.getText().toString(),
                        txtContact.getText().toString());
                if(isUpdate = true){
                    Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_LONG).show();
                }
                txtID.setText("");
                txtRestaurant.setText("");
                txtrdate.setText("");
                txtName.setText("");
                txtNumPeople.setText("");
                txtContact.setText("");
            }
        });
        getAndSetIntentData();

    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("restaurant") && getIntent().hasExtra("rdate") &&
                getIntent().hasExtra("name") && getIntent().hasExtra("numpeople") &&
                getIntent().hasExtra("contact")){
            //Getting Data from Intent
            selectedID = getIntent().getExtras().getString("id");
            restaurant = getIntent().getExtras().getString("restaurant");
            rdate = getIntent().getExtras().getString("rdate");
            name = getIntent().getExtras().getString("name");
            numpeople = getIntent().getExtras().getString("numpeople");
            contact = getIntent().getExtras().getString("contact");

            //Setting Intent Data
            txtID.setText(selectedID);
            txtRestaurant.setText(restaurant);
            txtrdate.setText(rdate);
            txtName.setText(name);
            txtNumPeople.setText(numpeople);
            txtContact.setText(contact);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

}