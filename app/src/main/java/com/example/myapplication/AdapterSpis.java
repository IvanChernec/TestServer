package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AdapterSpis extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Person> res1;
    private Context context;
    public static View view1;


    public AdapterSpis(@NonNull Context context, List<Person> res1) {
        this.inflater = LayoutInflater.from(context);
        this.res1 = res1;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder { ;
        final AppCompatTextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_spis);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view1 = inflater.inflate(R.layout.item, parent, false);

        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Person res = res1.get(position);
        ((MyViewHolder) holder).textView.setText(res.username);
    }

    @Override
    public int getItemCount() {return res1.size();}
}