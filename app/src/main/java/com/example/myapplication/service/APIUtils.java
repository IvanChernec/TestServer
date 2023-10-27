package com.example.myapplication.service;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL = "https://fakestoreapi.com/";

    public static ProductService getProductService(){
        return RetrofitClient.getClient(API_URL).create(ProductService.class);
    }

}