package com.cse437.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AudioService extends Service {
    MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String extra = intent.getStringExtra("voice");
        if (extra.equals("cow")) {
            mPlayer = MediaPlayer.create(AudioService.this, R.raw.moo);
        } else if (extra.equals("cat")) {
            mPlayer = MediaPlayer.create(AudioService.this, R.raw.meow);
        } else if (extra.equals("duck")) {
            mPlayer = MediaPlayer.create(AudioService.this, R.raw.quack);
        } else if (extra.equals("monkey")) {
            mPlayer = MediaPlayer.create(AudioService.this, R.raw.monkey);
        }


        mPlayer.setLooping(false);
        mPlayer.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mPlayer.stop();
        mPlayer.reset();
        mPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}
