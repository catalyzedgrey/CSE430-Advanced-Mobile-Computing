package com.cse437.myapplication.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.cse437.myapplication.R;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView tv;
    GridView gridView;

    PlayerFragment playerFragment;
    ContentFragment contentFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentSetup(savedInstanceState);
    }

    public void FragmentSetup(Bundle savedInstanceState){
        contentFragment = new ContentFragment();
        playerFragment = new PlayerFragment();
        fragmentManager = this.getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_frag, contentFragment, "content");
            fragmentTransaction.add(R.id.player_frag, playerFragment, "player");
            fragmentTransaction.commit();
        } else {
            contentFragment = (ContentFragment) fragmentManager.findFragmentByTag("content");
            playerFragment = (PlayerFragment) fragmentManager.findFragmentByTag("player");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

}
