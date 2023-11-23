package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.wisedrivemechanicapp.AddEngineVideos;
import com.wisedrive.wisedrivemechanicapp.AddTransmissionVideos;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineImages;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineVideo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterTransVideo extends  RecyclerView.Adapter<AdapterTransVideo.RecyclerViewHolder>
{
    ArrayList<PojoEngineImages> videosLists;
    Context context;
    MediaPlayer mediaPlayer;
    public VideoView view1;
    public AdapterTransVideo(ArrayList<PojoEngineImages> videosLists, Context context) {
        this.videosLists = videosLists;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public AdapterTransVideo.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_videos_lists, parent, false);
        return new AdapterTransVideo.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterTransVideo.RecyclerViewHolder holder, int position) {
        PojoEngineImages recyclerdata=videosLists.get(position);
        holder.video_name.setText(recyclerdata.getPart_name());
        holder.audio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(recyclerdata.getPart_id().equals("11")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(recyclerdata.getPart_id().equals("12")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
            }
        });

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTransmissionVideos.getInstance().capture_video();
            }
        });

    }

    @Override
    public int getItemCount() {
        return videosLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView video_name;
        ImageView audio;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            audio=itemView.findViewById(R.id.audio);
            video_name=itemView.findViewById(R.id.video_name);
            view1=itemView.findViewById(R.id.view1);
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


