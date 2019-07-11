package com.cse437.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText usernameET;
    Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usernameET = findViewById(R.id.username_et);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!usernameET.getText().toString().equals("") && usernameET.getText() != null){

                    startActivity(new Intent(getApplicationContext(), GameActivity.class).putExtra("newId", usernameET.getText().toString())
                    );
                }


            }
        });




    }

}
