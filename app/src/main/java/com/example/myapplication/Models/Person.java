package com.example.myapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
    public Address address;
    @SerializedName("id")
    @Expose
    public int id;
    public String email;
    public String username;
    public String password;

    public Name name;
    public String phone;

}
class Name{
    public String firstname;
    public String lastname;
}

class Address {
    public Geolocation geolocation;
    public String street;
    public String city;
    public int number;
    public String zipcode;
}
class Geolocation{
    public double lat;
    public double long1;

}



