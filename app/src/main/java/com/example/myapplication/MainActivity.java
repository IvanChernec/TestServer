package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private String content;
    private int sch = 1;
    private List<Person> people;
    private AdapterSpis adapterSpis;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.rv_spis_tovar);
        Button btnFetch = findViewById(R.id.downloadBtn);
        people = new ArrayList<>();
        adapterSpis = new AdapterSpis(getBaseContext(), people);
        recyclerView.setAdapter(adapterSpis);



        btnFetch.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Загрузка....", Toast.LENGTH_SHORT).show();
            //contentView.setText("Загрузка...");
            new Thread(new Runnable() {
                public void run() {
                    try{
                        Gson g = new Gson();
                       //for (int i = 0; i < 10; i++){

                            content = getContent("https://fakestoreapi.com/users");


                            //if(!content.trim().substring(1,4).equals("null")) {
                                Type listType = new TypeToken<List<Person>>(){}.getType();
                                people = g.fromJson(content, listType);
                                recyclerView.post(() -> {
                                    adapterSpis.updateData(people);
                                    Log.d("e", people.get(0).username);
                                    Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                                });
                            /*}else {
                                break;
                            }*/
                       //}


                    }
                    catch (IOException ex){
                        recyclerView.post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();




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