package com.example.finalproj;

public class Booking {
    private int _id;
    private String _restaurant;
    private String _rdate;
    private String _name;
    private String _numpeople;
    private String _contact;

    public Booking(int id, String restaurant, String rdate, String name, String numpeople, String contact){
        this._id = id;
        this._restaurant = restaurant;
        this._rdate = rdate;
        this._name = name;
        this._numpeople = numpeople;
        this._contact = contact;
    }
    public int getID(){
        return  this._id;
    }
    public void setID(int id){
        this._id = id;
    }

    public String get_restaurant(){
        return this._restaurant;
    }
    public void set_restaurant(String restaurant){
        this._restaurant = restaurant;
    }

    public String get_rdate(){ return this._rdate; }
    public void set_rdate(String birthday){
        this._rdate = birthday;
    }

    public String get_name(){
        return this._name;
    }
    public void setName(String name){
        this._name = name;
    }

    public String get_numpeople(){
        return this._numpeople;
    }
    public void set_numpeople(String numpeople){
        this._restaurant = numpeople; }

    public String get_contact(){
        return this._contact;
    }
    public void setContact(String contact){this._contact = contact;}

    // Method to show registered members in View
    @Override
    public String toString() {
        return "Booking ID: " + _id
                + "\n" + "Restaurant: " + _restaurant
                + "\n" + "Reservation Date: " + _rdate
                + "\n" + "Name: " + _name
                + "\n" + "Number of People: " + _numpeople
                + "\n" + "Contact Number: " + _contact;
    }
}