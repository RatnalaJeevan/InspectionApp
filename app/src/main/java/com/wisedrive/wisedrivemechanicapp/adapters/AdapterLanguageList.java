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

import com.wisedrive.wisedrivemechanicapp.AddEngImages;
import com.wisedrive.wisedrivemechanicapp.HomePage;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.SelectLanguage;
import com.wisedrive.wisedrivemechanicapp.SubmitVehInspection;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoLanguagelist;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterLanguageList extends  RecyclerView.Adapter<AdapterLanguageList.RecyclerViewHolder>{
    ArrayList<PojoLanguagelist> languagelists;
    Context context;

    public AdapterLanguageList(ArrayList<PojoLanguagelist> languagelists, Context context) {
        this.languagelists = languagelists;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterLanguageList.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_languagelist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterLanguageList.RecyclerViewHolder holder, int position) {
        PojoLanguagelist recyclerdata=languagelists.get(position);
        holder.tv_language_name.setText(recyclerdata.getNative_language());

        if (languagelists.get(position).getIsselected().equals("y")) {
            holder.selected.setVisibility(View.VISIBLE);
            holder.unselected.setVisibility(View.GONE);
        } else {
            holder.unselected.setVisibility(View.VISIBLE);
            holder.selected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLanguage object = (SelectLanguage) context;

                for (int i = 0; i < languagelists.size(); i++)
                {
                    if (i == position) {
                        SPHelper.saveSPdata(context.getApplicationContext(), SPHelper.languageid, recyclerdata.getLanguage_id());
                        SPHelper.saveSPdata(context.getApplicationContext(), SPHelper.languagename, recyclerdata.getNative_language());

                        languagelists.get(i).setIsselected("y");
                        if(SPHelper.camefrom.equals("home"))
                        {
                            Intent intent=new Intent(context.getApplicationContext(), HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }else{
                            Intent intent=new Intent(context.getApplicationContext(), SubmitVehInspection.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                    } else {
                        languagelists.get(i).setIsselected("n");
                    }
                }
                object.adapterLanguageList.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return languagelists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_language_name;
        ImageView selected,unselected;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_language_name=itemView.findViewById(R.id.tv_language_name);
            selected=itemView.findViewById(R.id.selected);
            unselected=itemView.findViewById(R.id.unselected);
        }
    }
}
