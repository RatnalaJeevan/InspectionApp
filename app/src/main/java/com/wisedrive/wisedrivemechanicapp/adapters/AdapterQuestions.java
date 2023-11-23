package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.wisedrivemechanicapp.EngineInspectionDetails;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.SelectLanguage;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAnswerslists;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoQuestionsList;
import java.util.ArrayList;

public class AdapterQuestions extends  RecyclerView.Adapter<AdapterQuestions.RecyclerViewHolder>
{
    ArrayList<PojoQuestionsList> questionsLists;
    Context context;
    public AdapterAnswers adapterAnswers;
    RecyclerView rv_answerslist;
    ArrayList<PojoAnswerslists> answerslists;
    public AdapterQuestions(ArrayList<PojoQuestionsList> questionsLists, Context context) {
        this.questionsLists = questionsLists;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterQuestions.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_question_lists, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterQuestions.RecyclerViewHolder holder, int position) {
        PojoQuestionsList recyclerdata=questionsLists.get(position);
        holder.tv_questions.setText(recyclerdata.getInspection_question());
        //EngineInspectionDetails object = (EngineInspectionDetails) context;
        answerslists=new ArrayList<>();
        answerslists=recyclerdata.getAnswerList();
        adapterAnswers = new AdapterAnswers(answerslists, context.getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rv_answerslist.setLayoutManager(layoutManager);
        rv_answerslist.setAdapter(adapterAnswers);

        /*rv_answerslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EngineInspectionDetails object = (EngineInspectionDetails) context;
                if(questionsLists.get(position).getIs_multi_selection().equals("Y")){
                    //SPHelper.ismulti="Y";

                }else{
                    SPHelper.ismulti="N";

                }
                object.adapterQuestions.notifyDataSetChanged();
            }
        });*/
        //object.adapterQuestions.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return questionsLists.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tv_questions;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            tv_questions=itemView.findViewById(R.id.tv_questions);
            rv_answerslist=itemView.findViewById(R.id.rv_answerslist);
        }
    }
}
