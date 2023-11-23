package com.example.myapplication;

import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.Person;
import com.example.myapplication.Models.Product;
import com.example.myapplication.service.APIUtils;
import com.example.myapplication.service.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddFragment extends Fragment {


    ProductService userService;
    EditText edtUId;
    EditText edtUsername;
    Button btnSave;
    Button btnDel;
    TextView txtUId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        txtUId = view.findViewById(R.id.txtUId);
        edtUId = view.findViewById(R.id.edtUId);
        edtUsername = view.findViewById(R.id.edtUsername);
        btnSave =  view.findViewById(R.id.btnSave);
        btnDel = view.findViewById(R.id.btnDel);

        userService = APIUtils.getProductService();


        final String userId = "1";
        String userName = "1";

        edtUId.setText(userId);
        edtUsername.setText(userName);


        btnSave.setOnClickListener(v -> {
            Person u = new Person();
            u.username = edtUsername.getText().toString();
            if(userId != null && userId.trim().length() > 0){
                //update user
                updateUser(1, u);
            } else {
                //add user
                addUser(u);
            }
        });

        btnDel.setOnClickListener(v -> {
            deleteUser(Integer.parseInt(userId));
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        view.setOnClickListener(v ->{});

        return view;
    }

    public void addUser(Person u){
        Call<Person> call = userService.addPerson(u);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.isSuccessful()){
                    Log.e("Successful: ", "Successful");
                    Toast.makeText(getContext(), "Проверка успешна!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUser(int id, Person u){
        Call<Person> call = userService.updatePerson(id, u);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.isSuccessful()){
                    Log.e("Successful: ", "Successful");
                    Toast.makeText(getContext(), "Проверка успешна!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteUser(int id){
        Call<Person> call = userService.deletePerson(id);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.isSuccessful()){
                    Log.e("Successful: ", "Successful");
                    Toast.makeText(getContext(), "Проверка успешна", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}