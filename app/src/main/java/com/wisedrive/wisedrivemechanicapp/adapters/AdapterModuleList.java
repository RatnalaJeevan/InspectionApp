package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.wisedrivemechanicapp.AddEngImages;
import com.wisedrive.wisedrivemechanicapp.AddTransmissionImages;
import com.wisedrive.wisedrivemechanicapp.AddTransmissionVideos;
import com.wisedrive.wisedrivemechanicapp.AddVehicleImages;
import com.wisedrive.wisedrivemechanicapp.AddEngineVideos;
import com.wisedrive.wisedrivemechanicapp.EngineInspectionDetails;
import com.wisedrive.wisedrivemechanicapp.OBDReport;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.TransInspectionDetails;
import com.wisedrive.wisedrivemechanicapp.commonclasses.PlayAudio;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoModuleList;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterModuleList extends  RecyclerView.Adapter<AdapterModuleList.RecyclerViewHolder>
{
    ArrayList<PojoModuleList> moduleLists;
    Context context;
    MediaPlayer mediaPlayer;
    public AdapterModuleList(ArrayList<PojoModuleList> moduleLists, Context context) {
        this.moduleLists = moduleLists;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterModuleList.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_module_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterModuleList.RecyclerViewHolder holder, int position)
    {
        PojoModuleList recyclerdata=moduleLists.get(position);
        holder.tv_module_images.setText(recyclerdata.getModule_name());

        holder.rl_modules.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                SPHelper.moduleid=recyclerdata.getModule_id();
                if(recyclerdata.getModule_id().equals("1")){
                    Intent intent=new Intent(context.getApplicationContext(), AddVehicleImages.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("2")){
                    Intent intent=new Intent(context.getApplicationContext(), AddEngImages.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("3")){
                    Intent intent=new Intent(context.getApplicationContext(), AddTransmissionImages.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("4")){
                    Intent intent=new Intent(context.getApplicationContext(), AddEngineVideos.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("5")){
                    Intent intent=new Intent(context.getApplicationContext(), AddTransmissionVideos.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("6")){
                    Intent intent=new Intent(context.getApplicationContext(), EngineInspectionDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("7")){
                    Intent intent=new Intent(context.getApplicationContext(), TransInspectionDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else if(recyclerdata.getModule_id().equals("8")){
                    Intent intent=new Intent(context.getApplicationContext(), OBDReport.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        //PlayAudio audio=new PlayAudio();
        holder.play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerdata.getModule_id().equals("1")){
                   SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("2")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("3")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("4")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("5")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("6")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("7")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getModule_id().equals("8")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_module_images;
        RelativeLayout rl_modules;
        ImageView play_audio;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_module_images=itemView.findViewById(R.id.tv_module_images);
            rl_modules=itemView.findViewById(R.id.rl_modules);
            play_audio=itemView.findViewById(R.id.play_audio);
        }
    }
    private void playAudio() {

        // String audioUrl = "https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(SPHelper.audiourl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        // Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
}
