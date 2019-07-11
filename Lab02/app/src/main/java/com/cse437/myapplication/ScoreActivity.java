package com.cse437.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    DBAdapter db;
    ListView scoreLV;
    long id;
    ArrayList<ListDetails> det;


    Button restartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        det = new ArrayList<>();
        openDB();

        scoreLV = findViewById(R.id.score_lv);
        id = getIntent().getLongExtra("newId", 0);
        det = new ArrayList<ListDetails>();

        Cursor cursor = db.getAllRows();
        displayRecordSet(cursor);

        CustomAdapter adapter = new CustomAdapter(this, det);

        scoreLV.setAdapter(adapter);

        restartBtn = findViewById(R.id.restart_btn);

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


    // Display an entire recordset to the screen.
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_NAME);
                int score = cursor.getInt(DBAdapter.COL_SCORE);
                String timestamp = cursor.getString(DBAdapter.COL_TIMESTAMP);
                det.add(new ListDetails(name, score, timestamp));


                //Log.d("DATABASE:", message);
                // [TO_DO_B6]
                // Create arraylist(s)? and use it(them) in the list view
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();


        // [TO_DO_B7]
        // Update the list view

        // [TO_DO_B8]
        // Display a Toast message
    }

    private void openDB() {
        db = new DBAdapter(this);
        db.open();
    }

    private void closeDB() {
        db.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


}
