package com.example.binder3u.popituserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialisation();
    }

    public void initialisation() {
        // Get reference of widgets from XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner_prenoms);

        // Initializing a String Array
        String[] names = new String[]{
                "Camille",
                "Micheline",
                "Claire",
                "Quentin",
                "Mouloud"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,names
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }


}
