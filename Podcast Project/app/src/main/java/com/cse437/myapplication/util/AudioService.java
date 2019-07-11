package com.cse437.myapplication.util;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class AudioService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    //    static MediaPlayer player;
    //media player
    private MediaPlayer player;
    //current position
    private int prevPosition;
    private String prevUrl;
    private String currentUrl;
//    static String audioUrl;

    private final IBinder musicBind = new MusicBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        InitMusicPlayer();


    }

    public void InitMusicPlayer() {
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }


    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        //audioUrl = intent.getStringExtra("sourceUrl");
//
//        try {
//           // player.setDataSource(audioUrl);
//            player.prepareAsync();
//        }catch(Exception e){
//
//        }
//        player.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
        super.onDestroy();
    }

    public class MusicBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }


    public void playSong(String url) {
        player.reset();
        currentUrl = url;
        try {
            player.setDataSource(url);
            //player.setOnCompletionListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();


    }

    public void pauseSong(String url) {
        if (player.isPlaying()) {
            prevUrl = url;
            prevPosition = player.getCurrentPosition();
            player.pause();
        }
    }

    public void SeekForwardTenSeconds() {
        if (player.isPlaying() && player.getCurrentPosition() + 1000 <= player.getDuration()) {
            prevPosition = player.getCurrentPosition() + 10000;
            player.seekTo(prevPosition);
        }
    }

    public void RewindTenSeconds() {
        if (player.isPlaying() && player.getCurrentPosition() - 1000 >= 0) {
            prevPosition = player.getCurrentPosition() - 10000;
            player.seekTo(prevPosition);
        }
    }


//    public static String getSource(){
//        return audioUrl;
//    }

    public boolean IsPlaying() {
        if (player != null) {
            return player.isPlaying();
        }
        return false;
    }


    public int getDuration() {
        return player.getDuration();
    }

    public int getCurrentPositon() {
        return player.getCurrentPosition();

    }


    public MediaPlayer getPlayer() {
        return player;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (currentUrl.equals(prevUrl)) {
            player.seekTo(prevPosition);
        }
        mp.start();
    }
}
