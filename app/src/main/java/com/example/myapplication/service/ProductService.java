package com.example.myapplication.service;


import com.example.myapplication.Models.Person;
import com.example.myapplication.Models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {

    @GET("products/")
    Call<List<Product>> getUsers();
    @GET("users/")
    Call<List<Person>> getPerson();

    @POST("add/")
    Call<Product> addUser(@Body Product user);

    @PUT("update/{id}")
    Call<Product> updateUser(@Path("id") int id, @Body Product user);

    @DELETE("delete/{id}")
    Call<Product> deleteUser(@Path("id") int id);
}
