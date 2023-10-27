package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Models.Person;
import com.example.myapplication.Models.Product;
import com.example.myapplication.adapter.AdapterProduct;
import com.example.myapplication.adapter.AdapterSpis;
import com.example.myapplication.service.APIUtils;
import com.example.myapplication.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String content;
    private List<Person> people;
    private List<Product> products;
    private AdapterSpis adapterSpis;
    private AdapterProduct adapterProduct;
    private RecyclerView recyclerView;
    ProductService productService;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.rv_spis_tovar);
        Button btnFetch = findViewById(R.id.downloadBtn);
        Button btnProduct = findViewById(R.id.downloadBtnPr);
        people = new ArrayList<>();
        products = new ArrayList<>();
        adapterProduct = new AdapterProduct(getBaseContext(), products);
        adapterSpis = new AdapterSpis(getBaseContext(), people);
        productService = APIUtils.getProductService();



        btnProduct.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Загрузка....", Toast.LENGTH_SHORT).show();
            getProductsList();

        });
        btnFetch.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Загрузка....", Toast.LENGTH_SHORT).show();
            getUsersList();
        });
    }
    public void getProductsList(){
        Call<List<Product>> call = productService.getUsers();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    products = response.body();
                    recyclerView.setAdapter(adapterProduct);
                    adapterProduct.updateData(products);
                    Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getUsersList(){
        Call<List<Person>> call = productService.getPerson();
        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if(response.isSuccessful()){
                    people = response.body();
                    recyclerView.setAdapter(adapterSpis);
                    adapterSpis.updateData(people);
                    Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection =(HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            reader= new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}