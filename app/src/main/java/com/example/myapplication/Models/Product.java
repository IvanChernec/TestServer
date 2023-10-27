package com.example.myapplication.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    public int id;


    public String title;

    public double price;

    public String description;

    public String category;

    public String image;

    public Rating rating;

}
class Rating{

    public double rate;

    public int count;

}
