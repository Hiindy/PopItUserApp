package com.example.binder3u.popituserapp;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener {
    private Spinner spinner;
    private Boolean btn1pressed = false;
    private GestureLibrary mLibrary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialisation();

        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!mLibrary.load()) {
            finish();
        }

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
    }

    public void initialisation() {
        // Get reference of widgets from XML layout
        spinner = (Spinner) findViewById(R.id.spinner_prenoms);

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
                this, R.layout.spinner_item, names
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        final Button button = findViewById(R.id.button_1);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("TAG","Pressed");
                btn1pressed = true;
            }
        });


//
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        btn1pressed = true;
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        btn1pressed = false;
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    public void passagePret() {
        Intent intent = new Intent(MainActivity.this, PretActivity.class);
        intent.putExtra("prenom", spinner.getSelectedItem().toString());
        startActivity(intent);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String result = predictions.get(0).name;

            if (btn1pressed && "password".equalsIgnoreCase(result)) {
                Log.i("TAG","Validate");
                passagePret();
            }
        }

    }
}
