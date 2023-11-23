package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterEngineVideo;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineVideo;

import java.util.ArrayList;

public class AddExhaustVideos extends AppCompatActivity {
    public GridView rv_exhaust_videos;
    public AdapterEngineVideo adapterEngineVideo;
    public ArrayList<PojoEngineVideo> engineVideos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhaust_videos);

        rv_exhaust_videos=(GridView)findViewById(R.id.rv_exhaust_videos);


    }
}