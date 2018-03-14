package com.example.binder3u.popituserapp;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PretActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener {
    private GestureLibrary mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pret);
        TextView textView_prenom = (TextView) findViewById(R.id.textView_prenom);
        Intent intent = getIntent();
        textView_prenom.setText(intent.getStringExtra("prenom"));


        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!mLibrary.load()) {
            finish();
        }

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
    }

    public void passageQuestion() {
        Intent intent = new Intent(PretActivity.this, QuestionActivity.class);
        startActivity(intent);
    }


    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {
        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String result = predictions.get(0).name;

            if ("validate".equalsIgnoreCase(result)) {
                passageQuestion();
            }
        }

    }
}
