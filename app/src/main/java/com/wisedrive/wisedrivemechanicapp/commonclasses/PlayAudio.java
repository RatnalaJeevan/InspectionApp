package com.wisedrive.wisedrivemechanicapp.commonclasses;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wisedrive.wisedrivemechanicapp.HomePage;

import java.io.IOException;

public class PlayAudio extends AppCompatActivity
{
    private static PlayAudio instance;

    MediaPlayer mediaPlayer;

    public static void setInstance(PlayAudio instance) {
        PlayAudio.instance = instance;
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
    public static PlayAudio getInstance() {
        return instance;
    }
}
