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

import com.wisedrive.wisedrivemechanicapp.EngineInspectionDetails;
import com.wisedrive.wisedrivemechanicapp.HomePage;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.SelectLanguage;
import com.wisedrive.wisedrivemechanicapp.SubmitVehInspection;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAnswerslists;

import java.util.ArrayList;

public class AdapterAnswers extends  RecyclerView.Adapter<AdapterAnswers.RecyclerViewHolder>{
    ArrayList<PojoAnswerslists> answerslists;
    Context context;

    public AdapterAnswers(ArrayList<PojoAnswerslists> answerslists, Context context) {
        this.answerslists = answerslists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAnswers.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_answers_lists, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterAnswers.RecyclerViewHolder holder, int position) {
        PojoAnswerslists recyclerdata=answerslists.get(position);
        holder.tv_options.setText(recyclerdata.getInspection_answer());

        if (answerslists.get(position).getIsSelected().equals("y")) {
            holder.selected.setVisibility(View.VISIBLE);
            holder.unselected.setVisibility(View.GONE);
        } else {
            holder.unselected.setVisibility(View.VISIBLE);
            holder.selected.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < answerslists.size(); i++)
                {
                    if (i == position) {
                        answerslists.get(i).setIsSelected("y");
                    } else {
                        answerslists.get(i).setIsSelected("n");
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return answerslists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_options;
        ImageView selected,unselected;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            tv_options=itemView.findViewById(R.id.tv_options);
            selected=itemView.findViewById(R.id.selected);
            unselected=itemView.findViewById(R.id.unselected);
        }
    }
}
