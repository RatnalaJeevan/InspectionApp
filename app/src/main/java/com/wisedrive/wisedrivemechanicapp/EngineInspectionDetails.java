package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterModuleList;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterQuestions;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddInspectionDetails;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAnswerDetails;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoQuestionsList;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EngineInspectionDetails extends AppCompatActivity {
    RecyclerView rv_questions;
    public AdapterQuestions adapterQuestions;
    ArrayList<PojoQuestionsList> questionsLists;
    RelativeLayout rl_engine,rl_trans;
    TextView tv_trans,tv_engine;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    TextView tv_save_next;
    ImageView go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eng_inspect_details);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(EngineInspectionDetails.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_engine=(TextView)findViewById(R.id.tv_engine);
        tv_trans=(TextView)findViewById(R.id.tv_trans);
        rl_engine=(RelativeLayout)findViewById(R.id.rl_engine);
        rl_trans=(RelativeLayout)findViewById(R.id.rl_trans);
        rv_questions=(RecyclerView)findViewById(R.id.rv_questions);
        tv_save_next=(TextView)findViewById(R.id.tv_save_next);
        tv_save_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EngineInspectionDetails.this,SubmitVehInspection.class);
                startActivity(intent);
            }
        });
        go_back=(ImageView)findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_eng_questionlist();
    }

    public void ontrans_select(View view) {
        rl_trans.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_trans.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.orange));
        tv_trans.setTextColor(Color.parseColor("#FFFFFF"));
        rl_engine.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_engine.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_engine.setTextColor(Color.parseColor("#FF000000"));
    }

    public void onengine_select(View view) {
        rl_engine.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_engine.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.orange));
        tv_engine.setTextColor(Color.parseColor("#FFFFFF"));
        rl_trans.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_trans.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_trans.setTextColor(Color.parseColor("#FF000000"));
    }

    public void get_eng_questionlist()
    {
        if(!Connectivity.isNetworkConnected(EngineInspectionDetails.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_questions_list(SPHelper.getSPData(EngineInspectionDetails.this, SPHelper.languageid, ""),SPHelper.moduleid);
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
                            adapterQuestions = new AdapterQuestions(questionsLists, EngineInspectionDetails.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(EngineInspectionDetails.this, LinearLayoutManager.VERTICAL, false);
                            rv_questions.setLayoutManager(layoutManager2);
                            rv_questions.setAdapter(adapterQuestions);
                            EngineInspectionDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterQuestions.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(EngineInspectionDetails.this, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(EngineInspectionDetails.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    public  void post_eng_questionslist() throws JSONException
    {
            if(!Connectivity.isNetworkConnected(EngineInspectionDetails.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_LONG).show();
            }else
            {
                progressDialog.show();

                JSONArray paramsarr = new JSONArray();
                for (int i = 0; i <questionsLists.size(); i++)
                {
                    JSONObject obbb = new JSONObject();

                    obbb.put("inspection_question_id","");
                    obbb.put("inspection_answer_id", "");
                    paramsarr.put(obbb);
                }

                PojoAddInspectionDetails pojoAddInspectionDetails=new PojoAddInspectionDetails(SPHelper.vehid,SPHelper.getSPData(EngineInspectionDetails.this, SPHelper.dealerid, "")
                        ,SPHelper.vehid,paramsarr);
                Call<AppResponse> call =  apiInterface.add_inspection_details(pojoAddInspectionDetails);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                Intent intent=new Intent(getApplicationContext(), SubmitVehInspection.class);
                                startActivity(intent);

                            } else if (response_code.equals("300"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(EngineInspectionDetails.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(EngineInspectionDetails.this, "internal server error" , Toast.LENGTH_SHORT).show();
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