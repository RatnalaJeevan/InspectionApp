package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.wisedrivemechanicapp.HomePage;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoDealerLists;

import java.util.ArrayList;

public class AdapterDealerLists extends  RecyclerView.Adapter<AdapterDealerLists.RecyclerViewHolder>{
    ArrayList<PojoDealerLists> dealerLists;
    Context context;

    public AdapterDealerLists(ArrayList<PojoDealerLists> dealerLists, Context context) {
        this.dealerLists = dealerLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDealerLists.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_dealerlist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterDealerLists.RecyclerViewHolder holder, int position) {
        PojoDealerLists recyclerdata=dealerLists.get(position);
        holder.dealername.setText(recyclerdata.getDealer_name());
        holder.dealerno.setText(recyclerdata.getPhone_no());
        //holder.iv_dealerlogo.setImageResource(recyclerdata.getDealerlogo());
        if (recyclerdata.getDealer_logo() != null && !recyclerdata.getDealer_logo().isEmpty() && !recyclerdata.getDealer_logo().equals("null"))
        {
            Glide.with(context).load(recyclerdata.getDealer_logo()).placeholder(R.drawable.icon_noimage).into(holder.iv_dealerlogo);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.dealerselected="y";
               // Toast.makeText(context,"selected yes",Toast.LENGTH_LONG).show();

                SPHelper.saveSPdata(context.getApplicationContext(), SPHelper.dealerid, recyclerdata.getDealer_id());
                HomePage.getInstance().selected_dealer.setText(recyclerdata.getDealer_name());
                HomePage.getInstance().selected_vehno.requestFocus();
                HomePage.getInstance().rl_dealerlist.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dealerLists.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder{
        ImageView iv_dealerlogo;
        TextView dealername,dealerno;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            dealerno=itemView.findViewById(R.id.dealerno);
            dealername=itemView.findViewById(R.id.dealername);
            iv_dealerlogo=itemView.findViewById(R.id.iv_dealerlogo);
        }
    }
}
