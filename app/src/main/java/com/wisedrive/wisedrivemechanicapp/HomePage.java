package com.wisedrive.wisedrivemechanicapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterDealerLists;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterLanguageList;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterMainSelectedLanguage;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoDealerLists;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoLanguagelist;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HomePage extends AppCompatActivity {
    boolean isPLAYING=false;
    public RelativeLayout rl_popup,transparent_layout,rl_dealerlist,rl_inspect,
                 rl_transparent_1,rl_addcar,rl_transparent_2,rl_language,transparent_layout2;
    public TextView start_inspection,submit_details,add_car,reinspect,noresults,tv_logout,tv_selected_language;
    ImageView iv_translate,iv_record,iv_mic;
    RecyclerView rv_dealer_list,rv_language_list;
    ArrayList<PojoLanguagelist> languagelists;
    public AdapterMainSelectedLanguage adapterLanguageList;
    ArrayList<PojoDealerLists> dealerLists;
    AdapterDealerLists adapterDealerLists;
    public EditText selected_dealer,selected_vehno;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    private  Dialog dialog;
    private static HomePage instance;
    TextView yes,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        instance=this;
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(HomePage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_language_list=(RecyclerView) findViewById(R.id.rv_language_list);
        rl_language=(RelativeLayout)findViewById(R.id.rl_language);
        iv_mic=(ImageView)findViewById(R.id.iv_mic);
        tv_logout=(TextView)findViewById(R.id.tv_logout);
        tv_selected_language=(TextView)findViewById(R.id.tv_selected_language);
        noresults=(TextView)findViewById(R.id.noresults);
        start_inspection=(TextView)findViewById(R.id.start_inspection);
        submit_details=(TextView)findViewById(R.id.submit_details);
        add_car=(TextView)findViewById(R.id.add_car);
        reinspect=(TextView)findViewById(R.id.reinspect);
        rv_dealer_list=(RecyclerView)findViewById(R.id.rv_dealer_list);
        rl_popup=(RelativeLayout)findViewById(R.id.rl_popup);
        rl_dealerlist=(RelativeLayout)findViewById(R.id.rl_dealerlist);
        iv_translate=(ImageView)findViewById(R.id.iv_translate);
        iv_record=(ImageView)findViewById(R.id.iv_record);
        transparent_layout=(RelativeLayout)findViewById(R.id.transparent_layout);
        rl_transparent_1=(RelativeLayout)findViewById(R.id.rl_transparent_1);
        rl_inspect=(RelativeLayout)findViewById(R.id.rl_inspect);
        rl_transparent_2=(RelativeLayout)findViewById(R.id.rl_transparent_2);
        rl_addcar=(RelativeLayout)findViewById(R.id.rl_addcar);
        selected_vehno=(EditText)findViewById(R.id.selected_vehno);
        selected_dealer=(EditText)findViewById(R.id.selected_dealer);
        tv_selected_language.setText(SPHelper.getSPData(HomePage.this, SPHelper.languagename, ""));
        start_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_dealer.setText("");
                selected_vehno.setText("");
                noresults.setVisibility(View.GONE);
                rl_popup.setVisibility(View.VISIBLE);
                selected_dealer.requestFocus();
            }
        });
        transparent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_popup.setVisibility(View.GONE);
            }
        });
        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(HomePage.this,SelectCarBrand.class);
                    startActivity(intent);
            }
        });
        reinspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this, SubmitVehInspection.class);
                startActivity(intent);
            }
        });

        rl_transparent_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_inspect.setVisibility(View.GONE);
            }
        });
        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_dealer.getText().toString().equals("")){
                    Toast.makeText(HomePage.this,"Select Dealer",Toast.LENGTH_LONG).show();
                }
                else if(selected_vehno.getText().toString().equals("")){
                    Toast.makeText(HomePage.this,"Select Vehicle number",Toast.LENGTH_LONG).show();

                }else if(selected_vehno.getText().length()<7){
                    Toast.makeText(HomePage.this,"Select valid Vehicle number",Toast.LENGTH_LONG).show();
                }else{
                    check_eligibility();
                }
            }
        });
        iv_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,SelectLanguage.class);
                startActivity(intent);
            }
        });
        tv_selected_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.camefrom="home";
                Intent intent=new Intent(HomePage.this,SelectLanguage.class);
                startActivity(intent);
            }
        });
        rl_transparent_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_addcar.setVisibility(View.GONE);
            }
        });
        iv_record.setOnClickListener(new View.OnClickListener()
        {
            MediaPlayer mp = new MediaPlayer();
            @Override
            public void onClick(View v)
            {
                if (!isPLAYING) {
                    isPLAYING = true;
                    Toast.makeText(HomePage.this,"clicked",Toast.LENGTH_LONG).show();
                    MediaPlayer mp = new MediaPlayer();
                    try {
                        mp.setDataSource("https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3");
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                    }
                } else {
                    Toast.makeText(HomePage.this,"not clicked",Toast.LENGTH_LONG).show();
                    isPLAYING = false;
                    stopPlaying();
                }
            }
            private void stopPlaying() {
                mp.release();
                mp = null;

            }
        });
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVoiceDialog();

            }
        });
        dialog = new Dialog(HomePage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_logout_dialog);
        dialog.setCancelable(true);
        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(HomePage.this, SPHelper.employeeid, "");
                SPHelper.saveSPdata(HomePage.this, SPHelper.languageid, "");
                SPHelper.saveSPdata(HomePage.this, SPHelper.languagename, "");
                Intent i = new Intent(HomePage.this,LoginPage.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        if(SPHelper.getSPData(HomePage.this, SPHelper.languageid, "").equals("")){
            rl_language.setVisibility(View.VISIBLE);
        }else{
            rl_language.setVisibility(View.GONE);
        }
        search();

//        if(SPHelper.dealerselected.equalsIgnoreCase("y")){
//Toast.makeText(HomePage.this,"selected yes",Toast.LENGTH_LONG).show();
//
//        }else {
//
//
//        }

        get_languagelist();
    }

    public  void openVoiceDialog(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000 && resultCode==RESULT_OK){
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice=arrayList.get(0);
            selected_dealer.setText(voice);
        }else{
            Toast.makeText(HomePage.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
    public void search(){
        selected_dealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //selected_dealer.setCursorVisible(true);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //selected_dealer.setCursorVisible(true);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                SPHelper.dealerselected="";
                //selected_dealer.setCursorVisible(true);
                if(selected_dealer.getText().toString().trim().length()>=2)
                {
                    rl_dealerlist.setVisibility(View.VISIBLE);
                    get_dealerslist();

                }else{
                    rl_dealerlist.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onBackPressed()
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    public void get_dealerslist()
    {
        if(!Connectivity.isNetworkConnected(HomePage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_dealerlist(SPHelper.getSPData(HomePage.this, SPHelper.employeeid, ""),selected_dealer.getText().toString().trim());
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
                            dealerLists=new ArrayList<>();
                            dealerLists=appResponse.getResponse().getDealerList();
                            if(dealerLists.isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                                rl_dealerlist.setVisibility(View.GONE);
                            }else{
                                noresults.setVisibility(View.GONE);
                                rl_dealerlist.setVisibility(View.VISIBLE);
                            }
                            adapterDealerLists = new AdapterDealerLists(dealerLists, HomePage.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
                            rv_dealer_list.setLayoutManager(layoutManager2);
                            rv_dealer_list.setAdapter(adapterDealerLists);
                            HomePage.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterDealerLists.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(HomePage.this, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(HomePage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    public void check_eligibility()
    {
        SPHelper.vehno=selected_vehno.getText().toString().trim();
        if(!Connectivity.isNetworkConnected(HomePage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.chech_eligibility(SPHelper.getSPData(HomePage.this, SPHelper.dealerid, ""),selected_vehno.getText().toString().trim());
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
                            rl_popup.setVisibility(View.GONE);
                            String id=appResponse.getResponse().getVehicleEligibility().getInspection_status_id();
                            SPHelper.model_name=appResponse.getResponse().getVehicleEligibility().getVehicle_model();
                            SPHelper.brandlogo=appResponse.getResponse().getVehicleEligibility().getBrand_icon();
                            SPHelper.manufacture_year=appResponse.getResponse().getVehicleEligibility().getManufacturing_year();
                            SPHelper.fueltype=appResponse.getResponse().getVehicleEligibility().getFuel_type();
                            SPHelper.vehid=appResponse.getResponse().getVehicleEligibility().getVehicle_id();
                            SPHelper.transmissiontype=appResponse.getResponse().getVehicleEligibility().getTransmission_type();
                            if(id==null||id.equals("null")||id.equals(null)){
                                Toast.makeText(HomePage.this, "Vehicle is not requested for inspection", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(HomePage.this, SubmitVehInspection.class);
                                startActivity(intent);
                            }
                            else if(appResponse.getResponse().getVehicleEligibility().getInspection_status_id().equals("1")){
                                //got to submit vehicle page
                                Intent intent=new Intent(HomePage.this, SubmitVehInspection.class);
                                startActivity(intent);
                            }else{
                                rl_inspect.setVisibility(View.VISIBLE);
                            }

                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            rl_addcar.setVisibility(View.VISIBLE);
                            rl_popup.setVisibility(View.GONE);
                           /* Toast.makeText(HomePage.this, "Vehicle doesnot exists", Toast.LENGTH_SHORT).show();
                            submit_details.setVisibility(View.GONE);
                            add_car.setVisibility(View.VISIBLE);*/
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(HomePage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    public void get_languagelist()
    {
        if(!Connectivity.isNetworkConnected(HomePage.this))
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
                            adapterLanguageList = new AdapterMainSelectedLanguage(languagelists, HomePage.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
                            rv_language_list.setLayoutManager(layoutManager2);
                            rv_language_list.setAdapter(adapterLanguageList);
                            HomePage.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterLanguageList.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(HomePage.this, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(HomePage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    public static HomePage getInstance() {
        return instance;
    }
}