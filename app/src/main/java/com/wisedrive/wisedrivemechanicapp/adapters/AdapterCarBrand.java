package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.SelectCarModel;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarBrands;
import java.util.ArrayList;

public class AdapterCarBrand extends  RecyclerView.Adapter<AdapterCarBrand.RecyclerViewHolder>{
    ArrayList<PojoCarBrands> carBrands;
    Context context;

    public AdapterCarBrand(ArrayList<PojoCarBrands> carBrands, Context context) {
        this.carBrands = carBrands;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCarBrand.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_carbrands, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterCarBrand.RecyclerViewHolder holder, int position) {
        PojoCarBrands recyclerdata=carBrands.get(position);
        holder.tv_brandname.setText(recyclerdata.getCar_brand());
        if (recyclerdata.getBrand_icon() != null && !recyclerdata.getBrand_icon().isEmpty() && !recyclerdata.getBrand_icon().equals("null")) {
            Glide.with(context).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.brand_logo);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SPHelper.carbrandid=recyclerdata.getId();
                SPHelper.brandlogo=recyclerdata.getBrand_icon();
                Intent intent=new Intent(context.getApplicationContext(), SelectCarModel.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carBrands.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder{
        ImageView brand_logo;
        TextView tv_brandname;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            tv_brandname=itemView.findViewById(R.id.tv_brandname);
            brand_logo=itemView.findViewById(R.id.brand_logo);
        }
    }
}
