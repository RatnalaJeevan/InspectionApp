package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterQuestions;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoQuestionsList;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransInspectionDetails extends AppCompatActivity {
    RecyclerView rv_trans_questions;
    AdapterQuestions adapterQuestions;
    ArrayList<PojoQuestionsList> questionsLists;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    TextView tv_save_next;
    ImageView go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_inspection_details);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(TransInspectionDetails.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_trans_questions=(RecyclerView)findViewById(R.id.rv_trans_questions);
        tv_save_next=(TextView)findViewById(R.id.tv_save_next);
        get_trans_question_list();
        go_back=(ImageView)findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_save_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TransInspectionDetails.this,SubmitVehInspection.class);
                startActivity(intent);
            }
        });
    }

    public void get_trans_question_list()
    {
        if(!Connectivity.isNetworkConnected(TransInspectionDetails.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_questions_list(SPHelper.getSPData(TransInspectionDetails.this, SPHelper.languageid, ""),SPHelper.moduleid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    // assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        progressDialog.dismiss();
                        if (response_code.equals("200"))
                        {
                            progressDialog.dismiss();
                            questionsLists=new ArrayList<>();
                            questionsLists=appResponse.getResponse().getQuestionList();
                            adapterQuestions = new AdapterQuestions(questionsLists, TransInspectionDetails.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(TransInspectionDetails.this, LinearLayoutManager.VERTICAL, false);
                            rv_trans_questions.setLayoutManager(layoutManager2);
                            rv_trans_questions.setAdapter(adapterQuestions);
                            TransInspectionDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterQuestions.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(TransInspectionDetails.this, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(TransInspectionDetails.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}