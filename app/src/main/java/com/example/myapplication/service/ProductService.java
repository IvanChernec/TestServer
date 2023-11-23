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

    @POST("products/")
    Call<Product> addProduct(@Body Product product);
    @POST("users/")
    Call<Person> addPerson(@Body Person user);

    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") int id, @Body Product product);

    @PUT("users/{id}")
    Call<Person> updatePerson(@Path("id") int id, @Body Person user);

    @DELETE("products/{id}")
    Call<Product> deleteProduct(@Path("id") int id);

    @DELETE("users/{id}")
    Call<Person> deletePerson(@Path("id") int id);
}
