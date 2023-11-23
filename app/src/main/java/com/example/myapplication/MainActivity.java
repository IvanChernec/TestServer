package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private ItemTouchHelper.SimpleCallback callback;


    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.rv_spis_tovar);
        Button btnFetch = findViewById(R.id.downloadBtn);
        Button btnProduct = findViewById(R.id.downloadBtnPr);
        Button btnAdd = findViewById(R.id.btn_add);
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

        btnAdd.setOnClickListener(v->{
            AddFragment addFragment = new AddFragment();
            this.getSupportFragmentManager().beginTransaction().add(R.id.fl_main, addFragment).commit();
        });

        callback = new ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position= viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT){
                    if (position >= 0) {
                        addUser(new Person());
                    }
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

    public void addUser(Person u){
        Call<Person> call = productService.addPerson(u);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.isSuccessful()){
                    Log.e("Successful: ", "Successful");
                    Toast.makeText(getApplicationContext(), "Проверка успешна!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteUser(int id){
        Call<Person> call = productService.deletePerson(id);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.isSuccessful()){
                    Log.e("Successful: ", "Successful");
                    Toast.makeText(getApplicationContext(), "Проверка успешна", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}