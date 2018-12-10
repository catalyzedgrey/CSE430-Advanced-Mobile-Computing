package com.cse437.myapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.cse437.myapplication.R;
import com.cse437.myapplication.util.DBAdapter;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView tv;
    GridView gridView;

    PlayerFragment playerFragment;
    ContentFragment contentFragment;

    FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    public static DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_pod:
                        Fragment g = getSupportFragmentManager().findFragmentByTag("content");
                        if(g != null)
                        {
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, g).commit();
                        }else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, new ContentFragment()).commit();
                        }

                        return true;
                    case R.id.search:
                        SearchFragment f = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, f).commit();
                        // TODO
                        return true;
                    case R.id.action_settings:
                        SearchFragment fd = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, fd).commit();
                        return true;
                }

                return false;
            }
        });

        FragmentSetup(savedInstanceState);
        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }
    public void FragmentSetup(Bundle savedInstanceState){
        contentFragment = new ContentFragment();
        playerFragment = new PlayerFragment();
        fragmentManager = this.getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_frag, contentFragment, "content");

            //fragmentTransaction.add(R.id.player_frag, playerFragment, "player");
            fragmentTransaction.add(R.id.player_frag, playerFragment, "player");
            fragmentTransaction.commit();
        } else {
            contentFragment = (ContentFragment) fragmentManager.findFragmentByTag("content");
            playerFragment = (PlayerFragment) fragmentManager.findFragmentByTag("player");

        }
    }



}
