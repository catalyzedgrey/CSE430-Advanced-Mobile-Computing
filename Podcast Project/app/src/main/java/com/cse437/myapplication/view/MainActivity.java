package com.cse437.myapplication.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cse437.myapplication.R;
import com.cse437.myapplication.model.Episode;
import com.cse437.myapplication.model.Podcast;
import com.cse437.myapplication.util.AudioService;
import com.cse437.myapplication.util.DBAdapter;
import com.cse437.myapplication.util.DbBitmapUtility;

import java.util.ArrayList;

//import com.cse437.myapplication.util.DBAdapter;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnEpisodesLoaded, PodcastFragment.EpisodeSwitcher, EpisodeFragment.OnPlay {


    PlayerFragment playerFragment;
    PodcastFragment podcastFragment;

    FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    public static DBAdapter myDb;
    public ArrayList<Podcast> podcasts = new ArrayList<>();

    private AudioService audioService;
    private Intent playIntent;
    private boolean musicBound = false;

    ImageView playPauseBtn;
    ImageView seekForwardBtn;
    ImageView seekBackwardsBtn;

    @Override
    protected void onStart() {
        openDB();
        PopulateList(myDb.getAllRows());

        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, AudioService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }

    }


    private boolean IsUniqueFeed(String url) {
        for (Podcast p : podcasts) {
            if (url.equals(p.getFeedUrl()))
                return false;
        }
        return true;
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.MusicBinder binder = (AudioService.MusicBinder) service;
            //get service
            audioService = binder.getService();
            //pass list
            //musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    private void PopulateList(Cursor cursor) {
        String message = "";
        podcasts.clear();
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                String name = cursor.getString(DBAdapter.COL_NAME);
                String feed = cursor.getString(DBAdapter.COL_URL);
                String art = cursor.getString(DBAdapter.ART_URL);
                byte[] img = cursor.getBlob(DBAdapter.COL_IMAGE);
                Drawable d = DbBitmapUtility.getImage(getResources(), img);
                Podcast p = new Podcast(name, feed, art);
                p.setImg(d);
                this.podcasts.add(p);

            } while (cursor.moveToNext());
        }

        cursor.close();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (podcasts == null) {
            podcasts = new ArrayList<>();
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_pod:
                        PodcastFragment g = (PodcastFragment) getSupportFragmentManager().findFragmentByTag("content");
                        if (g != null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, g).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new PodcastFragment(), "content").commit();
                        }

                        return true;
                    case R.id.action_search:
                        SearchFragment sF = (SearchFragment) getSupportFragmentManager().findFragmentByTag("search");
                        if (sF != null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, sF, "search").commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new SearchFragment(), "search").commit();
                        }
                        //SearchFragment f = new SearchFragment();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, f, "search").commit();
                        // TODO
                        return true;
                    case R.id.action_settings:
                        MediaFragment mF = (MediaFragment ) getSupportFragmentManager().findFragmentByTag("media");
                        if (mF != null) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, mF, "media").commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new MediaFragment(), "media").commit();
                        }
                        return true;
                }

                return false;
            }
        });


        FragmentSetup(savedInstanceState);

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        closeDB();

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof SearchFragment) {
            SearchFragment searchFragment = (SearchFragment) fragment;
            searchFragment.setOnEpisodesLoaded(this);
        } else if (fragment instanceof PodcastFragment) {
            PodcastFragment episodeFragment = (PodcastFragment) fragment;
            episodeFragment.setOnEpisodeSwitch(this);
        } else if (fragment instanceof EpisodeFragment) {
            EpisodeFragment episodeFragment = (EpisodeFragment) fragment;
            episodeFragment.setOnPlay(this);
        }


    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    public void FragmentSetup(Bundle savedInstanceState) {
        podcastFragment = new PodcastFragment();
        playerFragment = new PlayerFragment();
        fragmentManager = this.getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_frag, podcastFragment, "content");

            //fragmentTransaction.add(R.id.player_frag, playerFragment, "player");
            fragmentTransaction.add(R.id.player_frag, playerFragment, "player");
            fragmentTransaction.commit();
        } else {
            podcastFragment = (PodcastFragment) fragmentManager.findFragmentByTag("content");
            playerFragment = (PlayerFragment) fragmentManager.findFragmentByTag("player");

        }
    }

    @Override
    public void onEpisodeAdded(Podcast podcast) {
        if (IsUniqueFeed(podcast.getFeedUrl())) {
            podcasts.add(podcast);
            byte[] img = DbBitmapUtility.getByteArrayFromDrawable(podcast.getImg());
            final long newId = myDb.insertRow(podcast.getArtistName(), podcast.getFeedUrl(), podcast.getArtworkUrl600(), img);
            Toast.makeText(this, "Podcast was added successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Podcast was already added", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onEpisodeSwitch(Podcast podcast) {
        EpisodeFragment episodeFragment = (EpisodeFragment) getSupportFragmentManager().findFragmentByTag("episode");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (episodeFragment == null) {
            episodeFragment = new EpisodeFragment();
            episodeFragment.setCurrentPodcast(podcast);
            fragmentTransaction.replace(R.id.main_frag, episodeFragment, "episode").commit();
            fragmentTransaction.addToBackStack(null);
            //fragmentTransaction.commit();
        } else {
            fragmentTransaction.replace(R.id.main_frag, episodeFragment, "episode").commit();
            fragmentTransaction.addToBackStack(null);
        }
    }

    @Override
    public void onPlay(final Episode episode) {
        playPauseBtn = findViewById(R.id.play_pause_btn);
        seekForwardBtn = findViewById(R.id.fast_forward_btn);
        seekBackwardsBtn = findViewById(R.id.rewind_btn);

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioService.IsPlaying()) {
                    audioService.pauseSong(episode.url);
                    playPauseBtn.setImageResource(R.drawable.play);
                } else {
                    playPauseBtn.setImageResource(R.drawable.pause);
                    audioService.playSong(episode.url);
                }
            }
        });

        seekForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioService.IsPlaying()){
                    audioService.SeekForwardTenSeconds();
                }
            }
        });

        seekBackwardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioService.IsPlaying()){
                    audioService.RewindTenSeconds();
                }
            }
        });

        if (!audioService.IsPlaying()) {
            playPauseBtn.setImageResource(R.drawable.pause);
            audioService.playSong(episode.url);
        }


    }


}
