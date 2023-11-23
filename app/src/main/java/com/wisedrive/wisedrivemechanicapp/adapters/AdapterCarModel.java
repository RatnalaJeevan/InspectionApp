package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.SelectCarFuelMnf;
import com.wisedrive.wisedrivemechanicapp.SelectCarModel;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarModels;

import java.util.ArrayList;

public class AdapterCarModel extends  RecyclerView.Adapter<AdapterCarModel.RecyclerViewHolder>{
    ArrayList<PojoCarModels> carModels;
    Context context;

    public AdapterCarModel(ArrayList<PojoCarModels> carModels, Context context)
    {
        this.carModels = carModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCarModel.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_carmodels, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterCarModel.RecyclerViewHolder holder, int position)
    {
        PojoCarModels recyclerdata=carModels.get(position);
        holder.tv_modelname.setText(recyclerdata.getCar_model());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    SPHelper.carmodelid=recyclerdata.getModel_id();
                    SPHelper.model_name=recyclerdata.getCar_model();
                Intent intent=new Intent(context.getApplicationContext(), SelectCarFuelMnf.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_modelname;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            tv_modelname=itemView.findViewById(R.id.tv_modelname);
        }
    }
}
