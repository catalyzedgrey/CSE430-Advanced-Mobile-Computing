package com.cse437.lab4;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AudioService extends Service {

    MediaPlayer mpAudio;
    @Override
    public void onCreate() {
        super.onCreate();
        mpAudio = MediaPlayer.create(this, R.raw.rays_of_light);
        mpAudio.setLooping(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mpAudio.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mpAudio.stop();
        super.onDestroy();
    }
}

