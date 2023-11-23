package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterDealerLists;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterLanguageList;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoDealerLists;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoLanguagelist;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectLanguage extends AppCompatActivity {

    RecyclerView rv_language_list;
    ArrayList<PojoLanguagelist> languagelists;
    public AdapterLanguageList adapterLanguageList;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        rv_language_list=(RecyclerView)findViewById(R.id.rv_language_list);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(SelectLanguage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        get_languagelist();
    }

    public void get_languagelist()
    {
        if(!Connectivity.isNetworkConnected(SelectLanguage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_languagelist();
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
                            languagelists=new ArrayList<>();
                            languagelists=appResponse.getResponse().getLanguageList();
                            adapterLanguageList = new AdapterLanguageList(languagelists, SelectLanguage.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(SelectLanguage.this, LinearLayoutManager.VERTICAL, false);
                            rv_language_list.setLayoutManager(layoutManager2);
                            rv_language_list.setAdapter(adapterLanguageList);
                            SelectLanguage.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterLanguageList.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(SelectLanguage.this, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(SelectLanguage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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