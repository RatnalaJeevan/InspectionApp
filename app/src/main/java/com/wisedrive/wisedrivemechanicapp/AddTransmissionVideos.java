package com.wisedrive.wisedrivemechanicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterEngineImages;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterEngineVideo;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterTransVideo;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineImages;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineVideo;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransmissionVideos extends AppCompatActivity {
    public RecyclerView rv_car_trans_videlist;
    public AdapterTransVideo adapterEngineVideo;
    public ArrayList<PojoEngineImages> engineVideos;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    ImageView go_back;
    private static AddTransmissionVideos instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        go_back=(ImageView)findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        instance=this;
        setContentView(R.layout.activity_add_transmission_videos);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(AddTransmissionVideos.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_car_trans_videlist=(RecyclerView) findViewById(R.id.rv_car_trans_videlist);
        get_trans_videolist();
    }

    public  void capture_video(){
        Intent i=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(i, 1111);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1111 && data!=null){
            adapterEngineVideo.view1.setVideoURI(data.getData());
            adapterEngineVideo.view1.start();
        }
    }
    public  void get_trans_videolist()
    {
        {
            if (!Connectivity.isNetworkConnected(AddTransmissionVideos.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_LONG).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_partlist(SPHelper.moduleid,SPHelper.getSPData(AddTransmissionVideos.this,SPHelper.languageid,""));
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null)
                        {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                engineVideos=new ArrayList<>();
                                engineVideos=appResponse.getResponse().getEmgineimagelist();
                                adapterEngineVideo = new AdapterTransVideo(engineVideos,AddTransmissionVideos.this);
                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddTransmissionVideos.this,RecyclerView.VERTICAL,false);
                                rv_car_trans_videlist.setLayoutManager(linearLayoutManager);
                                rv_car_trans_videlist.setAdapter(adapterEngineVideo);
                                AddTransmissionVideos.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterEngineVideo.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddTransmissionVideos.this, "internal server error", Toast.LENGTH_SHORT).show();
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
    public static AddTransmissionVideos getInstance() {
        return instance;
    }
}