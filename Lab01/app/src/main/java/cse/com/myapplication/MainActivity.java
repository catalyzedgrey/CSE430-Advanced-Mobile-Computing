package cse.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Spinner fromSpinner, toSpinner;
    Button convertBtn;
    EditText valueET;
    TextView results;
    boolean exists = false;
    double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            result = Double.parseDouble(savedInstanceState.getString("result"));
            exists = savedInstanceState.getBoolean("exists");
            results = findViewById(R.id.results);
            if (exists) {
                results.setText(savedInstanceState.getString("result"));
            }else {
                results.setText(R.string.results);
            }



        }

        fromSpinner = findViewById(R.id.from_spinner);
        toSpinner = findViewById(R.id.to_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        results = findViewById(R.id.results);

        valueET = findViewById(R.id.value_et);

        convertBtn = findViewById(R.id.convert_button);
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!valueET.getText().toString().equals("") && valueET.getText() != null) {
                    if (fromSpinner.getSelectedItem().toString().equals("USD") && toSpinner.getSelectedItem().toString().equals("EGP")) {
                        String r1 = valueET.getText().toString();
                        result = Double.parseDouble(r1);
                        result *= 17.6;
                        results.setText(result + "");
                        exists = true;


                    } else if (fromSpinner.getSelectedItem().toString().equals("EGP") && toSpinner.getSelectedItem().toString().equals("USD")) {
                        String r1 = valueET.getText().toString();
                        result = Double.parseDouble(r1);
                        result /= 17.6;
                        results.setText(result + "");
                        exists = true;

                    } else {
                        Toast.makeText(getApplicationContext(), "Please make sure the currencies are not the same", Toast.LENGTH_LONG).show();
                        exists = false;
                    }
                } else {
                    results.setText(R.string.results);
                    result = 0;
                    Toast.makeText(getApplicationContext(), "Please make sure the the value isn't empty", Toast.LENGTH_LONG).show();
                    exists = false;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("result", result + "");
        outState.putBoolean("exists", exists);
        super.onSaveInstanceState(outState);
    }
}
