package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Models.Product;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Product> res1;
    private Context context;
    public static View view1;


    public AdapterProduct(@NonNull Context context, List<Product> res1) {
        this.inflater = LayoutInflater.from(context);
        this.res1 = res1;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder { ;
        final AppCompatTextView textView;
        final AppCompatImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_spis_pr);
            imageView = itemView.findViewById(R.id.iv_pr);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view1 = inflater.inflate(R.layout.item_product, parent, false);

        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product res = res1.get(position);
        ((MyViewHolder) holder).textView.setText(res.title);
        Picasso.get().load(res.image).into(((MyViewHolder) holder).imageView);
    }
    public void updateData(List<Product> viewModels) {
        res1.clear();
        res1.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {return res1.size();}
}
