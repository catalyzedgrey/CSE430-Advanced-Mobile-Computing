package com.cse437.myapplication.util;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.cse437.myapplication.R;

public class AudioService extends Service {
    static MediaPlayer mpAudio;

    @Override
    public void onCreate() {
        super.onCreate();
        //mpAudio= new MediaPlayer();
        mpAudio = MediaPlayer.create(this, R.raw.slowmotion);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String audioUrl = intent.getStringExtra("sourceUrl");


        mpAudio.setAudioStreamType(AudioManager.STREAM_MUSIC);

//        try {
//            //mpAudio.setDataSource(audioUrl);
//
//            mpAudio.prepare();
//        }catch(Exception e){
//
//        }
        mpAudio.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mpAudio.stop();
        super.onDestroy();
    }

    public void SetSource(String audioUrl){
        try {
            //mpAudio.setDataSource(audioUrl);
            mpAudio.prepare();
        }catch(Exception e){

        }
    }

    public boolean IsPlaying(){
        if (mpAudio != null){
            return mpAudio.isPlaying();
        }
        return false;
    }

    public static MediaPlayer getMpAudio(){
        return mpAudio;
    }
}
