package com.cse437.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {


    ImageView img0, img1, img2, img3, img4, img5, img6, img7;
    int flipCount, score;

    DBAdapter myDb;

    List<String> animalList;
    List<ImageView> imgs;
    List<ImageView> flippedImages;
    public List<String> flippedForReal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        //det = new ArrayList<>();

        openDB();
        //myDb.deleteAll();
        final String name = getIntent().getStringExtra("newId");
        getImgIDs();
        FillAndShuffleAnimalList();

        flipCount = 0;
        score = 0;

        final long timestamp = System.currentTimeMillis() / 1000L;
        final long newId = myDb.insertRow(name, 0, timestamp + "");

        //displayRecordSet(cursor);
        for (int i = 0; i < 8; i++) {
            imgs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    flipCount++;
                    ImageView view = (ImageView) v;

                    String tag = view.getTag().toString();
                    switch (tag) {
                        case "cow":
                            view.setImageResource(R.drawable.cow);
                            break;

                        case "cat":
                            view.setImageResource(R.drawable.cat);
                            break;

                        case "duck":
                            view.setImageResource(R.drawable.duck);
                            break;

                        case "monkey":
                            view.setImageResource(R.drawable.monkey);
                            break;

                    }
                    if (flipCount == 1) {
                        flippedImages.add(view);
//                        flipCount++;
                    } else if (flipCount == 2) {
                        if (flippedImages.size() == 1) {
                            flippedImages.add(view);
                        }

                        if(flippedImages.size()==2)
                        {
                            if (flippedImages.get(0).getTag().toString().equals(flippedImages.get(1).getTag().toString())
                                    && !flippedImages.get(0).equals(flippedImages.get(1))) {
                                if(!flippedForReal.contains(view.getTag().toString())){
                                    flippedForReal.add(view.getTag().toString());
                                    flippedImages.get(0).setEnabled(false);
                                    flippedImages.get(1).setEnabled(false);
                                    Intent i = new Intent(GameActivity.this, AudioService.class);
                                    i.putExtra("voice", flippedImages.get(0).getTag().toString());
                                    startService(i);
                                    score++;
                                }

                                flipCount = 0;
                                if (flippedImages.size() == 2)
                                    flippedImages.clear();
                                if (flippedImages.size() == 2 && !flippedImages.get(0).equals(flippedImages.get(1))) {
                                    //Toast.makeText(getApplicationContext(), "Hooray", Toast.LENGTH_SHORT).show();
                                    score++;
                                } else if (flippedImages.size() == 1 && flippedImages.get(0).equals(flippedImages.get(1))) {
                                    new TimeoutOperation();
                                }

                            }else {
                                new TimeoutOperation().execute();
                            }
                        }


                    }else {
                        flipCount=0;
                    }

                    if (score == 4) {
                        Toast.makeText(getApplicationContext(), "Hooray", Toast.LENGTH_SHORT).show();
                        long newtimestamp = System.currentTimeMillis() / 1000L;
                        long diffTimeStamp = newtimestamp - timestamp;

                        int finalScore;
                        if (diffTimeStamp <= 20) {
                            finalScore = 3;
                        } else if (diffTimeStamp > 20 && diffTimeStamp <= 60) {
                            finalScore = 2;
                        } else {
                            finalScore = 1;
                        }
                        myDb.updateRow(newId, name, finalScore, diffTimeStamp + "");

                        closeDB();
                        Intent i = new Intent(getApplicationContext(), ScoreActivity.class);
                        i.putExtra("Id", newId);
                        startActivity(i);
                        finish();


                    }
                }
            });

        }

    }




    private class TimeoutOperation extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try {

                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for (ImageView i : flippedImages) {
                i.setImageResource(R.drawable.question);
            }
            if (flippedImages.size() == 2)
                flippedImages.clear();

            flipCount = 0;
            //Update your layout here
            super.onPostExecute(result);
        }
    }

    private void FillAndShuffleAnimalList() {
        animalList = new ArrayList<>();
        flippedImages = new ArrayList<>();
        animalList.add("monkey");
        animalList.add("monkey");
        animalList.add("cat");
        animalList.add("cat");
        animalList.add("duck");
        animalList.add("duck");
        animalList.add("cow");
        animalList.add("cow");

        Collections.shuffle(animalList);

        for (int i = 0; i < 8; i++) {
            imgs.get(i).setTag(animalList.get(i));
        }
    }

    private void getImgIDs() {
        imgs = new ArrayList<>();

        img0 = findViewById(R.id.img0);
        imgs.add(img0);

        img1 = findViewById(R.id.img1);
        imgs.add(img1);

        img2 = findViewById(R.id.img2);
        imgs.add(img2);

        img3 = findViewById(R.id.img3);
        imgs.add(img3);

        img4 = findViewById(R.id.img4);
        imgs.add(img4);

        img5 = findViewById(R.id.img5);
        imgs.add(img5);

        img6 = findViewById(R.id.img6);
        imgs.add(img6);

        img7 = findViewById(R.id.img7);
        imgs.add(img7);
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }
}
